package org.isdp.vertx.mqtt.eventbus;

public interface IsdpMqttBus {
    /**
     * 订阅名称
     */
    public String topicName = null;
    /**
     * 消息体
     */
    public String payload = null;
    /**
     * qos
     */
    public int qos = 1;

}
