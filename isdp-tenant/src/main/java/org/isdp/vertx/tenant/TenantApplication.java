package org.isdp.vertx.tenant;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.servicediscovery.Record;
import io.vertx.servicediscovery.ServiceDiscovery;
import io.vertx.servicediscovery.ServiceDiscoveryOptions;
import io.vertx.servicediscovery.types.HttpEndpoint;
import org.isdp.vertx.boot.annotation.Application;
import org.isdp.vertx.boot.annotation.DeployVerticle;
import org.isdp.vertx.boot.boot.IsdpApplication;
import org.isdp.vertx.gateway.enmu.ServiceRegister;
import org.isdp.vertx.gateway.verticle.MicroVerticle;
import org.isdp.vertx.tenant.cmd.AuthCmd;
import org.isdp.vertx.tenant.cmd.TenantCmd;
import org.isdp.vertx.tenant.cmd.UsersCmd;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.Collections;

import static java.util.stream.Collectors.toList;


@Application(scanPackages = {"org.isdp.vertx.gateway","org.isdp.vertx.tenant"})
@DeployVerticle(name = "tenant")
public class TenantApplication extends AbstractVerticle implements MicroVerticle {

    Logger logger = LoggerFactory.getLogger(TenantApplication.class);

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        super.start(startPromise);

        Router router =Router.router(vertx);

        AuthCmd.create(vertx, router);
        TenantCmd.createTenant(vertx, router);
        UsersCmd.create(vertx, router);


        ServiceDiscovery discovery=  ServiceDiscovery.create(vertx, new ServiceDiscoveryOptions()
                .setBackendConfiguration(
                        new JsonObject()
                                .put("connectionString", "redis://localhost:6379")
                                .put("key", "records")
                ));
        ServiceRegister.setDiscovery(discovery);

        logger.info("router info is {}",router.getRoutes().stream().map(route -> route.getPath()).collect(toList()));

        // 发布至网关
        publishHttpEndpointService("isdp-auth", "127.0.0.1", 8081, "");
        vertx.createHttpServer().requestHandler(router).listen(8081).onSuccess(httpServer -> {
                logger.info("TenantApplication is starting and port is {}",httpServer.actualPort());
        });
    }

    public static void main(String[] args) {
        IsdpApplication isdpApplication = new IsdpApplication();
        isdpApplication.Run(TenantApplication.class, Vertx.vertx());
    }
}
