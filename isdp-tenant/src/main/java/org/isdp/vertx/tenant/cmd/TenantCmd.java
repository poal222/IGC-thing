package org.isdp.vertx.tenant.cmd;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.sqlclient.templates.RowMapper;
import org.isdp.vertx.common.cmd.CrudCmd;
import org.isdp.vertx.common.service.CrudService;
import org.isdp.vertx.tenant.model.TenantRowMapper;
import org.isdp.vertx.tenant.service.TenantService;

public class TenantCmd extends CrudCmd {
    private TenantCmd(Vertx vertx, Router router) {
        initCmd( vertx,  router);
    }

    @Override
    public CrudService getCrudService() {
        return TenantService.create();
    }

    @Override
    public RowMapper getRowMapper() {
        return TenantRowMapper.INSTANCE;
    }

    @Override
    public String getRoutePath() {
        return "/isdp/saas/tenant";
    }



    public static  TenantCmd createTenant(Vertx vertx, Router router){

        return new TenantCmd(vertx,router);

    }


}
