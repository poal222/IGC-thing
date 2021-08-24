package org.isdp.vertx.gateway;

import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import org.isdp.vertx.gateway.enmu.ServiceEnum;
import org.isdp.vertx.gateway.httpendpoiot.HttpEndpointOptions;

public class GatewayService {

    /**
     * 接受服务总入口
     */
    public void httpEndpoints(Router router){
        // 配置针对http请求的网关处理器
        router.route().handler(this::serviceHandler);

    }

    private void serviceHandler(RoutingContext routingContext) {
        //获取rest调用服务内容
        HttpEndpointOptions.create(routingContext);

        String uri = routingContext.request().uri();
//        String method =
    }

    /**
     *  1、处理http endpoionts方式的服务
     * @param serviceEnum
     */
    public void dispacheService(ServiceEnum serviceEnum){

    }
}
