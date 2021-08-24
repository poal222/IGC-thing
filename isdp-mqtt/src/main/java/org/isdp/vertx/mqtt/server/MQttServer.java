package org.isdp.vertx.mqtt.server;

import io.netty.handler.codec.mqtt.MqttQoS;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.json.JsonObject;
import io.vertx.core.net.PemKeyCertOptions;
import io.vertx.mqtt.MqttEndpoint;
import io.vertx.mqtt.MqttServer;
import io.vertx.mqtt.MqttServerOptions;
import io.vertx.mqtt.MqttTopicSubscription;
import io.vertx.mqtt.messages.MqttPublishMessage;
import io.vertx.mqtt.messages.MqttSubscribeMessage;
import io.vertx.mqtt.messages.MqttUnsubscribeMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * 1、首先，它会创建一个MqttServer 实例，
 * 2、然后使用 endpointHandler 方法指定一个处理器来处理远程客户端发送的CONNECT信息，一个MqttEndpoint实例会作为 handler 的参数传入，
 * 它携带了所有与CONNECT消息相关联的主要信息，
 * 例如客户端标识符，用户名/密码，"will"信息，session 清除标志，协议版本和保活超时等。
 * 在 handler 内, endpoint 实例提供 accept 方法以相应的 CONNACK 消息响应远程客户端，通过这种方式，成功建立连接。
 * 3、最后，通过listen方法启动一个默认的服务端（运行在 localhost 上并且默认 MQTT 端口为 1883）， 这个方法同样允许指定一个 handler 来检查是否服务器是否已经正常启动。
 */
public class MQttServer {
    private static Logger logger = LoggerFactory.getLogger(MQttServer.class);
    private boolean SSLEnabled = false;

    private MQttServer(Vertx vertx,JsonObject config) {
        if(config == null || config.isEmpty()){
            config = new JsonObject().put("mqttServer",new JsonObject().put("SSLEnabled",false));
        }
        SSLEnabled = config.getJsonObject("mqttServer").getBoolean("SSLEnabled");
        init(vertx,config);
    }

    public static MQttServer create(Vertx vertx){
      return create(vertx,null);
    }

    public static  MQttServer create(Vertx vertx, JsonObject config) {
        return new MQttServer(vertx,config);
    }

    private void init(Vertx vertx,JsonObject config) {
        MqttServerOptions options = new MqttServerOptions();
        // 是否启动ssl证书
        if (SSLEnabled) {
            options = addSSL(null, null);
        }

        MqttServer mqttServer = MqttServer.create(vertx);


        mqttServer.endpointHandler(endpoint -> {
                    // shows main connect info
                    System.out.println("MQTT client [" + endpoint.clientIdentifier() + "] request to connect, clean session = " + endpoint.isCleanSession());

                    if (endpoint.auth() != null) {
                        System.out.println("[username = " + endpoint.auth().getUsername() + ", password = " + endpoint.auth().getPassword() + "]");
                    }
                    if (endpoint.will() != null) {
                        System.out.println("[will topic = " + endpoint.will().getWillTopic() + " msg = " + new String(endpoint.will().getWillMessageBytes()) +
                                " QoS = " + endpoint.will().getWillQos() + " isRetain = " + endpoint.will().isWillRetain() + "]");
                    }

                    System.out.println("[keep alive timeout = " + endpoint.keepAliveTimeSeconds() + "]");

                    // accept connection from the remote client
                    endpoint.accept(false);
                    // 当远程客户端发送一个 DISCONNECT 消息来主动断开与服务端的连接，
                    endpoint.disconnectHandler(v -> {
                        if (logger.isInfoEnabled()) {
                            logger.info("Received disconnect from client ");
                        }
                    });
                    //处理客户端 订阅/退订 请求
                    endpoint.subscribeHandler(mqttSubscribeMessage -> subscribeClient(endpoint, mqttSubscribeMessage));
                    // 处理客户端的UNSUBSCRIBE消息
                    endpoint.unsubscribeHandler(unsubscribe -> unsubscribeClient(endpoint, unsubscribe));
                    /**
                     * 为了处理远程客户端发布的消息，MqttEndpoint接口提供了publishHandler方法来指定一个 handler, 这个handler接收一个
                     * MqttPublishMessage类型的实例作为参数，该实例 包含了载荷信息，QoS 等级以及复制和保留标识。
                     * 如果 QoS 等级是 0（AT_MOST_ONCE），endpoint 就没有必要回复客户端了。
                     *
                     * 如果 QoS 等级是 1（AT_LEAST_ONCE），endpoint 需要使用
                     * publishAcknowledge
                     *  方法回复一个 PUBACK 消息给客户端
                     *
                     * 如果 QoS 等级是 2（EXACTLY_ONCE），endpoint 需要使用
                     * publishReceived
                     *  方法回复一个PUBREC消息给客户端。 在这种情况下，这个 endpoint 同时也要通过
                     * publishReleaseHandler
                     *  指定一个 handler 来处理来自客户端的PUBREL（远程客户端接收到 endpoint 发送的 PUBREC 后发送的）消息 为了结束 QoS 等级为2的消息的传递，endpoint 可以使用
                     * publishComplete
                     *  方法发送一个 PUBCOMP 消息给客户端。
                     */
                    endpoint.publishHandler(message -> publishClient(endpoint, message));
                    endpoint.publishReleaseHandler(messageId -> {
                        endpoint.publishComplete(messageId);
                    });
                    // specifing handlers for handling QoS 1 and 2
                    endpoint.publishAcknowledgeHandler(messageId -> {

                        System.out.println("Received ack for message = " + messageId);

                    }).publishReceivedHandler(messageId -> {

                        endpoint.publishRelease(messageId);

                    }).publishCompletionHandler(messageId -> {

                        System.out.println("Received ack for message = " + messageId);
                    });

                    endpoint.pingHandler(v -> pingClient(endpoint));
                })
                .listen(ar -> {
                    if (ar.succeeded()) {

                        System.out.println("MQTT server is listening on port " + ar.result().actualPort());
                    } else {

                        System.out.println("Error on starting the server");
                        ar.cause().printStackTrace();
                    }
                });
    }

    /**
     * 发布消息到客户端
     * 可以使用
     * publish
     * 方法发布一个消息到远程客户端，该方法需要补充一下参数： 发布主题，消息载荷，QoS 等级，复制和保留标识。
     * 如果 QoS 等级是 0（AT_MOST_ONCE），endpoint 就不会收到任何客户端的响应
     * <p>
     * 如果 QoS 等级是 1（AT_LEAST_ONCE），endpoint 需要处理客户端的PUBACK消息,为了收到最后的确认消息，需要使用
     * publishAcknowledgeHandler
     * 指定一个handler来接收。
     * 如果 QoS 等级是 2（EXACTLY_ONCE），endpoint 需要处理客户端的PUBREC消息，可以通过
     * publishReceivedHandler
     * 方法指定一个handler来实现。 在这个handler内，endpoint 可以使用
     * publishRelease
     * 方法回复客户端 PUBREL 消息。最后一步是处理来自客户端的PUBCOMP消息作为已发布消息的最终确认。 这可以使用
     * publishCompletionHandler
     * 方法指定一个handler来处理最终接收到的 PUBCOMP 消息。
     *
     * @param endpoint
     * @param topic    消息主题
     * @param buffer   发布的消息
     */
    private void publishMessage(MqttEndpoint endpoint, String topic, Buffer buffer) {
        endpoint.publish(topic,
                buffer,
                MqttQoS.EXACTLY_ONCE,
                false,
                false);
    }

    private void publishClient(MqttEndpoint endpoint, MqttPublishMessage message) {
        System.out.println("Just received message [" + message.payload().toString(Charset.defaultCharset()) + "] with QoS [" + message.qosLevel() + "]");

        if (message.qosLevel() == MqttQoS.AT_LEAST_ONCE) {
            endpoint.publishAcknowledge(message.messageId());
        } else if (message.qosLevel() == MqttQoS.EXACTLY_ONCE) {
            endpoint.publishReceived(message.messageId());
        }
    }

    private void unsubscribeClient(MqttEndpoint endpoint, MqttUnsubscribeMessage unsubscribe) {
        for (String t : unsubscribe.topics()) {
            System.out.println("Unsubscription for " + t);
        }
        // ack the subscriptions request
        endpoint.unsubscribeAcknowledge(unsubscribe.messageId());
    }

    /**
     * 客户端保活通知
     * MQTT 底层的保活机制是由服务端内部处理的。当接收到CONNECT消息，服务端解析消息中指定的保活超时时间以便于检查客户端在这段时间内是否有发送消息， 与此同时，没收到一个 PINGREQ 消息，服务端都会回复一个相关的 PINGRESP 消息。
     *
     * @param endpoint
     */
    private void pingClient(MqttEndpoint endpoint) {
        endpoint.pingHandler(v -> {
            // TODO: 2021/8/24  添加 客户端心跳功能，设计为需要动态保存，但不是持久化
            System.out.println("Ping received from client");
        });
    }
    /**
     * 添加 SSL/TLS 方式来授权和加密 ，支持配置PEM格式的证书
     *
     * @param keyPath  密钥路径
     * @param certPath 证书路径
     * @return
     */
    private MqttServerOptions addSSL(String keyPath, String certPath) {
        MqttServerOptions options = new MqttServerOptions()
                .setPort(8883)
                .setKeyCertOptions(new PemKeyCertOptions()
                        .setKeyPath(keyPath)
                        .setCertPath(certPath))
                .setSsl(true);
        return options;
    }


    /**
     * 在客户端和服务端的连接建立后，客户端可以发送 SUBSCRIBE 消息以订阅主题。
     * MqttEndpoint 允许使用 subscribeHandler 方法来指定一个 handler 处理到来的订阅请求，
     * 这个 handler 接收一个 MqttSubscribeMessage
     * 类型的实例，该实例携带了主题列表以及客户端指定的 QoS 等级。
     * 最后，这个 endpoint 实例提供了subscribeAcknowledge
     * 方法来回复一个包含相关许可 QoS 等级的 SUBACK 消息给客户端。
     */
    private void subscribeClient(MqttEndpoint endpoint, MqttSubscribeMessage mqttSubscribeMessage) {
        List<MqttQoS> grantedQosLevels = new ArrayList<>();
        for (MqttTopicSubscription s : mqttSubscribeMessage.topicSubscriptions()) {
            System.out.println("Subscription for " + s.topicName() + " with QoS " + s.qualityOfService());
            grantedQosLevels.add(s.qualityOfService());
        }
        // ack the subscriptions request
        publishMessage(endpoint,"rpi2/temp",Buffer.buffer("测试测试测试！"));
        endpoint.subscribeAcknowledge(mqttSubscribeMessage.messageId(), grantedQosLevels);
    }

}
