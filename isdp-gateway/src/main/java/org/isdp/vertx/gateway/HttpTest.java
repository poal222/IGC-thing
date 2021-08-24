package org.isdp.vertx.gateway;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.servicediscovery.Record;
import io.vertx.servicediscovery.ServiceDiscovery;
import io.vertx.servicediscovery.types.HttpEndpoint;
import org.isdp.vertx.gateway.enmu.ServiceRegister;

public class HttpTest extends AbstractVerticle {

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        super.start(startPromise);
        ServiceDiscovery discovery = ServiceDiscovery.create(vertx);


// 由一个类型创建的record
        Record record = HttpEndpoint.createRecord("isdp-auth", "localhost", 8081, "/api");
        discovery.publish(record, ar -> {
            if (ar.succeeded()) {
                // 发布成功
                Record publishedRecord = ar.result();
                ServiceRegister.addServiceRegister(publishedRecord);

            } else {
                // 发布失败
            }
        });
        Router router = Router.router(vertx);
        router.route().handler(BodyHandler.create());
        router.route().handler(s->{
            System.out.println("sdsdsdsd");
            System.out.println(s.request());
            s.response().setStatusCode(400).end("ceshi wanchenbg");
        });
        vertx.createHttpServer().requestHandler(router)
                .listen(8081);
    }


}
