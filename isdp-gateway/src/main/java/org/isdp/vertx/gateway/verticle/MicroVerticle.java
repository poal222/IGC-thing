package org.isdp.vertx.gateway.verticle;

import io.vertx.servicediscovery.Record;
import io.vertx.servicediscovery.types.HttpEndpoint;
import org.isdp.vertx.gateway.enmu.ServiceRegister;

public interface MicroVerticle  {


        //发布http-endpoiont服务
        default void publishHttpEndpointService(String name,String host,int port,String root){
            Record record = HttpEndpoint.createRecord(name, host, port, root);
            ServiceRegister.discovery.publish(record, ar -> {
                if (ar.succeeded()) {
                    // 发布成功
                    Record publishedRecord = ar.result();
                    ServiceRegister.addServiceRegister(publishedRecord);

                } else {
                    // 发布失败
                    ar.cause().printStackTrace();
                }
            });
        }

}
