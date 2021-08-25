package org.isdp.vertx.gateway;

import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientResponse;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import io.vertx.servicediscovery.Record;
import io.vertx.servicediscovery.ServiceDiscovery;
import io.vertx.servicediscovery.ServiceReference;
import org.isdp.vertx.common.cmd.IsdpResponseWrapper;
import org.isdp.vertx.gateway.enmu.ServiceEnum;
import org.isdp.vertx.gateway.enmu.ServiceRegister;
import org.isdp.vertx.gateway.httpendpoiot.HttpEndpointOptions;

public class GatewayService implements IsdpResponseWrapper {

    private Vertx vertx;

    /**
     * 接受服务总入口
     */
    public void httpEndpoints(Vertx vertx,Router router){

        this.vertx = vertx;
        // 配置针对http请求的网关处理器
        router.route().handler(this::serviceHandler);


    }

    private void serviceHandler(RoutingContext routingContext) {
        //获取rest调用服务内容
        HttpEndpointOptions httpEndpointOptions =  HttpEndpointOptions.create(routingContext);

        //获取gateway-name
        String[] uris=httpEndpointOptions.getUri().split("/");

        if(uris.length<1) routingContext.response().end("resource not found!");
        // 分发请求
        dispacheService(httpEndpointOptions,routingContext);




    }

    public void dispacheService(HttpEndpointOptions httpEndpointOptions,RoutingContext routingContext){
        //获取gateway-name
        String[] uris=httpEndpointOptions.getUri().split("/");


        String gatewayName = uris[1];
        /**
         * 通过微服务发现，获取服务
         */
        ServiceDiscovery discovery = ServiceDiscovery.create(vertx);


        Record record = ServiceRegister.findRecord(gatewayName);

        ServiceReference httpclientServiceReference = discovery.getReference(record);
        // 获取 需要代理的httpclient元数据
//        HttpClient client = httpclientServiceReference.getAs(HttpClient.class);
        String uri = httpEndpointOptions.getUri().substring(gatewayName.length()+1);
        // 获取服务对象
        WebClient client = httpclientServiceReference.getAs(WebClient.class);

        // 你需要提供完整的path
        client.request(httpEndpointOptions.getMethod(),uri).send(
                response -> {
                   if(response.succeeded()){
                       HttpResponse httpResponse =  response.result();
                       if(httpResponse.statusCode()==400) error(routingContext,new RuntimeException(httpResponse.statusMessage()));
                    if(httpResponse.statusCode()==500) error(routingContext,new RuntimeException(httpResponse.statusMessage()));
                    if(httpResponse.statusCode()==200) successfulled(routingContext,httpResponse.bodyAsString());
                   }
                    if(response.failed()) error(routingContext,response.cause());
                    // 不要忘记释放服务资源
                    httpclientServiceReference.release();

                });
    }
//        // 定义完整的path
//        client.request(httpEndpointOptions.getMethod(), uri)
//                .compose(request ->
//                request.send(httpEndpointOptions.getBody().result())
//                .onSuccess(results -> {
//                    Future<Buffer> bufferFuture = results.body();
////                    results.body().onSuccess(buffer->{
////                        System.out.println(buffer.toString());
////                    });
//                    if(results.statusCode()==400) error(routingContext,new RuntimeException(results.statusMessage()));
//                    if(results.statusCode()==500) error(routingContext,new RuntimeException(results.statusMessage()));
//                    if(results.statusCode()==200) successfulled(routingContext,results.body().result());
//                })
//                .onComplete(ar2 -> {
//
//                    System.out.println("sdsd="+ar2.result());
//                    // 不要忘记释放服务资源
//                    httpclientServiceReference.release();
//                }));
//    }

    /**
     *  1、处理http endpoionts方式的服务
     * @param serviceEnum
     */
    public void dispacheService(ServiceEnum serviceEnum){

    }
}
