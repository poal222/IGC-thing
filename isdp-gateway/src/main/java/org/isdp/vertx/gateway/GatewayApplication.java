package org.isdp.vertx.gateway;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.servicediscovery.ServiceDiscovery;
import io.vertx.servicediscovery.ServiceDiscoveryOptions;
import org.isdp.vertx.gateway.enmu.ServiceEnum;
import org.isdp.vertx.gateway.enmu.ServiceRegister;
import org.isdp.vertx.gateway.httpendpoiot.HttpEndpointOptions;

public class GatewayApplication extends AbstractVerticle {




    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        super.start(startPromise);

        Router router = Router.router(vertx);
        GatewayService gatewayService =GatewayService.create(vertx,router);
        // 加载服务调用
        ServiceDiscovery discovery=  ServiceDiscovery.create(vertx, new ServiceDiscoveryOptions()
                .setBackendConfiguration(
                        new JsonObject()
                                .put("connectionString", "redis://localhost:6379")
                                .put("key", "records")
                ));
        ServiceRegister.setDiscovery(discovery);

        vertx.createHttpServer().requestHandler(router)
                .listen(8080);
    }


    public static void main(String[] args) {
        Vertx.vertx().deployVerticle(GatewayApplication::new,new DeploymentOptions());
        Vertx.vertx().deployVerticle(HttpTest::new,new DeploymentOptions());

    }


}
