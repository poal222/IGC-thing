package org.isdp.vertx.mqtt.eventbus;

import io.vertx.core.Vertx;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.MessageConsumer;

public class IsdpMqttEventBus {

    public static void publishMqttMesssage(EventBus eventBus){

        MessageConsumer<String> messageConsumer =  eventBus.consumer("publishMqttMesssage");
        messageConsumer.handler(message -> {
          String msg = message.body();
            System.out.println(msg);
        });



    }
}
