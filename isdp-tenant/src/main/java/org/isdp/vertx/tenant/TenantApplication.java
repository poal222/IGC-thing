package org.isdp.vertx.tenant;

import io.vertx.core.Vertx;
import org.isdp.vertx.boot.annotation.Application;
import org.isdp.vertx.boot.boot.IsdpApplication;


@Application(scanPackages = {"org.isdp.vertx.tenant"})
public class TenantApplication {
    public static void main(String[] args) {
        IsdpApplication isdpApplication = new IsdpApplication();
        isdpApplication.Run(TenantApplication.class, Vertx.vertx());
    }
}
