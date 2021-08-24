package org.isdp.vertx.mqtt;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.mqtt.MqttClient;
import io.vertx.mqtt.MqttClientOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * setClientId：用于设置客户端会话的ID。在setCleanSession(false);被调用时，MQTT服务器利用该ID获得相应的会话。此ID应少于23个字符，默认根据本机地址、端口和时间自动生成。
 * setCleanSession：若设为false，MQTT服务器将持久化客户端会话的主体订阅和ACK位置，默认为true。
 * setKeepAlive：定义客户端传来消息的最大时间间隔秒数，服务器可以据此判断与客户端的连接是否已经断开，从而避免TCP/IP超时的长时间等待。
 * setUserName：服务器认证用户名。
 * setPassword：服务器认证密码。
 * setWillTopic：设置“遗嘱”消息的话题，若客户端与服务器之间的连接意外中断，服务器将发布客户端的“遗嘱”消息。
 * setWillMessage：设置“遗嘱”消息的内容，默认是长度为零的消息。
 * setWillQos：设置“遗嘱”消息的QoS，默认为QoS.ATMOSTONCE。
 * setWillRetain：若想要在发布“遗嘱”消息时拥有retain选项，则为true。
 * 失败重连接设置说明
 * 网络出现故障时，程序能够自动重新连接并重建会话。利用下列方法能够配置重新连接的间隔和最大重试次数：
 *
 * setConnectAttemptsMax：客户端首次连接到服务器时，连接的最大重试次数，超出该次数客户端将返回错误。-1意为无重试上限，默认为-1。
 * setReconnectAttemptsMax：客户端已经连接到服务器，但因某种原因连接断开时的最大重试次数，超出该次数客户端将返回错误。-1意为无重试上限，默认为-1。
 * setReconnectDelay：首次重连接间隔毫秒数，默认为10ms。
 * setReconnectDelayMax：重连接间隔毫秒数，默认为30000ms。
 * setReconnectBackOffMultiplier：设置重连接指数回归。设置为1则停用指数回归，默认为2。
 */
public class MqttClientTest extends AbstractVerticle {

    private static Logger logger = LoggerFactory.getLogger(MqttClientTest.class);

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        super.start(startPromise);

        MqttClientOptions mqttClientOptions = new MqttClientOptions();

        mqttClientOptions.setWillTopic("die");
        mqttClientOptions.setWillFlag(true);
        mqttClientOptions.setWillMessage("offline");
        mqttClientOptions.setWillQoS(3);
        mqttClientOptions.setWillRetain(true);


        mqttClientOptions.setClientId("cjclksdclsjdc");
        mqttClientOptions.setUsername("mqttroot");
        mqttClientOptions.setUsername("mqttpassword");

        MqttClient client = MqttClient.create(vertx,mqttClientOptions);
        client.connect(1883, "localhost", s -> {
            System.out.println(s.succeeded());

//            client.disconnect();

            // 订阅主题消息
            client.publishHandler(s1 -> {
                        System.out.println("There are new message in topic: " + s1.topicName());
                        System.out.println("Content(as string) of the message: " + s1.payload().toString());
                        System.out.println("QoS: " + s1.qosLevel());

                    })
                    .subscribe("rpi2/temp", 2);
        });

    }

    public static void main(String[] args) {
        Vertx.vertx().deployVerticle(MqttClientTest::new,new DeploymentOptions());
    }
}
