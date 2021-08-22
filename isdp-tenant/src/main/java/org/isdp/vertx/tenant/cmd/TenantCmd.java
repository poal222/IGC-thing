package org.isdp.vertx.tenant.cmd;

import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import org.isdp.vertx.boot.annotation.DeployVerticle;
import org.isdp.vertx.boot.boot.IsdpApplication;
import org.isdp.vertx.common.cmd.CrudCmd;
import org.isdp.vertx.common.cmd.WebCmd;
import org.isdp.vertx.tenant.model.Tenant;
import org.isdp.vertx.tenant.model.TenantRowMapper;
import org.isdp.vertx.tenant.service.TenantService;

@DeployVerticle(name = "tenant")
public class TenantCmd extends CrudCmd {

    TenantService<Tenant> tenantTenantService = null;
    @Override
    public void start(Promise startPromise) throws Exception {
        super.start(startPromise);
//
        tenantTenantService = TenantService.create();
        setRowMapper(TenantRowMapper.INSTANCE);
        // 加载service
        setCrudService(tenantTenantService);
//        tag [1:注册 其它路由]
        UsersCmd.create(Vertx.vertx(),router).init(); ;
        //启动应用即可
        createHttpServer(router);
    }

//    private void addWebCmd(WebCmd webCmd) {
//
//    }
//
//    public static void main(String[] args) {
//        IsdpApplication isdpApplication = new IsdpApplication();
//        isdpApplication.Run(TenantCmd.class, Vertx.vertx());
//    }
}
