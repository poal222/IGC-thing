package org.isdp.vertx.tenant.cmd;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.sqlclient.templates.RowMapper;
import org.isdp.vertx.common.cmd.CrudCmd;
import org.isdp.vertx.common.cmd.WebCmd;
import org.isdp.vertx.common.service.CrudService;
import org.isdp.vertx.tenant.model.TenantRowMapper;
import org.isdp.vertx.tenant.service.TenantService;
import org.isdp.vertx.tenant.service.UsersService;

/**
 */
public class UsersCmd extends CrudCmd {

    private UsersCmd(Vertx vertx, Router router) {
        initCmd( vertx,  router);
    }

    @Override
    public CrudService getCrudService() {
        return UsersService.create();
    }

    @Override
    public RowMapper getRowMapper() {
        return TenantRowMapper.INSTANCE;
    }

    @Override
    public String getRoutePath() {
        return "/isdp/saas/user";
    }

    /**
     * 总入口
     * @param vertx
     * @param router
     * @return
     */

    public static UsersCmd create(Vertx vertx, Router router){

        return new UsersCmd(vertx,router);

    }

}
