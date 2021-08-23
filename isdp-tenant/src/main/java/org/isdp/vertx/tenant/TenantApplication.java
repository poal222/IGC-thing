package org.isdp.vertx.tenant;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import org.isdp.vertx.boot.annotation.Application;
import org.isdp.vertx.boot.annotation.DeployVerticle;
import org.isdp.vertx.boot.boot.IsdpApplication;
import org.isdp.vertx.tenant.cmd.AuthCmd;
import org.isdp.vertx.tenant.cmd.TenantCmd;
import org.isdp.vertx.tenant.cmd.UsersCmd;
import org.isdp.vertx.tenant.model.Tenant;


@Application(scanPackages = {"org.isdp.vertx.tenant"})
@DeployVerticle(name = "tenant")
public class TenantApplication extends AbstractVerticle {

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        super.start(startPromise);

        Router router =Router.router(vertx);

        AuthCmd.create(vertx, router);
        TenantCmd.createTenant(vertx, router);
        UsersCmd.create(vertx, router);


        System.out.println(router.getRoutes());
        vertx.createHttpServer().requestHandler(router).listen(8088).onSuccess(httpServer -> {
            System.out.println(httpServer.actualPort());
        });
    }

    public static void main(String[] args) {
        IsdpApplication isdpApplication = new IsdpApplication();
        isdpApplication.Run(TenantApplication.class, Vertx.vertx());
    }
}
