package org.isdp.vertx.gateway;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import org.isdp.vertx.gateway.enmu.ServiceEnum;
import org.isdp.vertx.gateway.enmu.ServiceRegister;
import org.isdp.vertx.gateway.httpendpoiot.HttpEndpointOptions;

public class GatewayApplication extends AbstractVerticle {




    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        super.start(startPromise);


        GatewayService gatewayService = new GatewayService();

        Router router = Router.router(vertx);
        gatewayService.httpEndpoints(vertx,router);
        vertx.createHttpServer().requestHandler(router)
                .listen(8080);
    }


    public static void main(String[] args) {
        Vertx.vertx().deployVerticle(GatewayApplication::new,new DeploymentOptions());
        Vertx.vertx().deployVerticle(HttpTest::new,new DeploymentOptions());

    }


}
