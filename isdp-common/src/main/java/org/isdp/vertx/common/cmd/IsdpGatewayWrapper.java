package org.isdp.vertx.common.cmd;


import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.servicediscovery.Record;
import io.vertx.servicediscovery.ServiceDiscovery;
import io.vertx.servicediscovery.ServiceDiscoveryOptions;
import io.vertx.servicediscovery.types.HttpEndpoint;

/**
 * 提供对gateway的支持
*/
public interface IsdpGatewayWrapper {

    /**
     * 代码级别推送至网关
     * 当前推送全部以rest方式配置
     * @param config
     */
    default  Future<Record> publishToGateway(Vertx vertx, JsonObject config){
        Record record1 = HttpEndpoint.createRecord(
                "some-http-service", // The service name
                "localhost", // The host
                8433, // the port
                "/api" // the root of the service
        );

        ServiceDiscovery discovery = ServiceDiscovery.create(vertx);
        // 自定义配置
        discovery = ServiceDiscovery.create(vertx,
                new ServiceDiscoveryOptions()
                        .setAnnounceAddress("service-announce")
                        .setName("my-name"));
       return discovery.publish(record1);

    }

}
