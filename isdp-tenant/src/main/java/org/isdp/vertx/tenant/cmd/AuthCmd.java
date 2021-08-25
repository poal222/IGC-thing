package org.isdp.vertx.tenant.cmd;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.User;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.auth.jwt.JWTAuthOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.JWTAuthHandler;
import io.vertx.ext.web.handler.SessionHandler;
import io.vertx.ext.web.sstore.ClusteredSessionStore;
import io.vertx.ext.web.sstore.LocalSessionStore;
import io.vertx.ext.web.sstore.SessionStore;
import io.vertx.mysqlclient.MySQLPool;
import io.vertx.sqlclient.Pool;
import io.vertx.sqlclient.PoolOptions;
import org.isdp.vertx.common.cmd.IsdpResponseWrapper;
import org.isdp.vertx.common.cmd.WebCmd;
import org.isdp.vertx.datasource.DataSourceProvider;
import org.isdp.vertx.tenant.exception.AuthParamException;
import org.isdp.vertx.tenant.service.AuthSqlClientService;
import org.isdp.vertx.tenant.service.UsersService;

/**
 */
public class AuthCmd implements WebCmd , IsdpResponseWrapper {

    private Vertx vertx;
    private Router router;

    private Pool pool = DataSourceProvider.sqlClient;


    JWTAuth jwt ;



    private AuthCmd(Vertx vertx, Router router) {

        this.vertx = vertx;
        this.router = router;
        JWTAuthOptions authConfig = new JWTAuthOptions();
        this.jwt=JWTAuth.create(vertx, authConfig);

        initialize();
    }

    @Override
    public String getRoutePath() {
        return "/isdp/saas/auth";
    }

    /**
     * 总入口
     * @param vertx
     * @param router
     * @return
     */

    public static AuthCmd create(Vertx vertx, Router router){

        return new AuthCmd(vertx,router);

    }

    /**
     * 定义认证特殊的方法
     */
    private void initialize(){


        router.route().handler(BodyHandler.create());

        router.post(getRoutePath()+"/login").handler(this::doLogin);
        router.post(getRoutePath()+"/logout").handler(this::doLogout);





    }

    /**
     *  注销
     * @param routingContext
     */
    private void doLogout(RoutingContext routingContext) {
        // 删除session

    }

    /**
     * 登录 支持多中认证方式，通过工厂模式加载
     * @param routingContext
     */
    private void doLogin(RoutingContext routingContext) {
        //1、获取认证信息
        JsonObject jsonObject = routingContext.getBodyAsJson();
        //获取认证方式
        String authType = jsonObject.getString("authType");
//        1、认证前校验
        beforeDoLogin(jsonObject,routingContext);
//                2、认证操作
        if("1".equalsIgnoreCase(authType)){
            // 执行认证
            AuthSqlClientService authSqlClientService = AuthSqlClientService.create(pool);

            authSqlClientService.authFuture(jsonObject).onSuccess(user->{
                //        3、认证后操作
                this.afterDoLogin(routingContext,user);

            }).onFailure(exception ->{
                error(routingContext,new RuntimeException(exception.getMessage()));
            });
        }

    }

    private void afterDoLogin(RoutingContext routingContext, User user) {
//        1、配置session
            initJwt( routingContext,  user);
        routingContext.setUser(user);


    }

    private void initJwt(RoutingContext routingContext, User user) {

        String token =  jwt.generateToken(new JsonObject().put("user", user.principal()));

        //        2、发证书
        JsonObject jsonObject =user.principal().put("token",token);

        successfulled(routingContext,jsonObject);
    }



//    private void initSession(RoutingContext routingContext, User user) {
//        SessionStore isdpStore = null;
//        /**
//         * 集群环境
//         */
//        if(vertx.isClustered()) {
//            // 使用默认配置创建集群session储存
//            isdpStore = ClusteredSessionStore.create(vertx,"ISDP.cluster.authSessionMap");
//
//
//        }else{
//            SessionStore store2 = LocalSessionStore.create(
//                    vertx,
//                    "ISDP.local.authSessionMap");
//        }
//        SessionHandler sessionHandler = SessionHandler.create(isdpStore);
//        sessionHandler.setUser(routingContext,user);
//
//        router.route().handler(sessionHandler);
//    }

    private void beforeDoLogin(JsonObject jsonObject, RoutingContext routingContext) {

//        异常校验
        if(jsonObject == null) error(routingContext,new AuthParamException("认证体不能为空！"));
        //获取认证方式
        String authType = jsonObject.getString("authType");
        if(authType == null) error(routingContext,new AuthParamException("认证方式不能为空！"));
        // authType =1 最简单的用户密码模式

    }

}
