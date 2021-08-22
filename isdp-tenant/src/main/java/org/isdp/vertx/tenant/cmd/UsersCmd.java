package org.isdp.vertx.tenant.cmd;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import org.isdp.vertx.common.cmd.WebCmd;
import org.isdp.vertx.tenant.service.UsersService;

/**
 * 通过init方式注册加载
 */
public class UsersCmd<T> extends WebCmd<T> {
    /**  service服务加载区域 **/
    private UsersService usersService;




    private UsersCmd(Vertx vertx, Router router) {
        super(vertx,router);
        setWebRoot("/isdp/user");
        usersService= UsersService.create();
    }

    public static UsersCmd create(Vertx vertx,Router router){
        UsersCmd usersCmd = new UsersCmd(vertx,router);
        return usersCmd;
    }

    public void init(){
        //定义 路由区域
        getRouter().post(getWebRoot()+"/ceshi").handler(routingContext->{
            routingContext.response().setStatusCode(200).end("this.sds");
        });

    }


}
