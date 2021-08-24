package org.isdp.vertx.mqtt;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import org.isdp.vertx.boot.annotation.Application;
import org.isdp.vertx.boot.annotation.DeployVerticle;
import org.isdp.vertx.boot.boot.ApplicationContext;
import org.isdp.vertx.boot.boot.IsdpApplication;
import org.isdp.vertx.mqtt.server.MQttServer;

@Application(scanPackages = "org.isdp.vertx.mqtt")
@DeployVerticle(name = "MQttApplication")
public class MQttApplication extends AbstractVerticle {

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        super.start(startPromise);
        MQttServer.create(vertx, ApplicationContext.APPLICAION_CONFIG);

    }

    public static void main(String[] args) {
        IsdpApplication isdpApplication = new IsdpApplication();
        isdpApplication.Run(MQttApplication.class, Vertx.vertx());
    }
}
