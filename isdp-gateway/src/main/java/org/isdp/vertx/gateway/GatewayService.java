package org.isdp.vertx.gateway;

import io.vertx.circuitbreaker.HystrixMetricHandler;
import io.vertx.core.Future;
import io.vertx.core.Vertx;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpClient;
import io.vertx.core.http.HttpClientResponse;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.jwt.JWTAuth;
import io.vertx.ext.auth.jwt.JWTAuthOptions;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.client.HttpRequest;
import io.vertx.ext.web.client.HttpResponse;
import io.vertx.ext.web.client.WebClient;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.ext.web.handler.HttpException;
import io.vertx.ext.web.handler.JWTAuthHandler;
import io.vertx.servicediscovery.Record;
import io.vertx.servicediscovery.ServiceDiscovery;
import io.vertx.servicediscovery.ServiceDiscoveryOptions;
import io.vertx.servicediscovery.ServiceReference;
import org.isdp.vertx.common.cmd.IsdpResponseWrapper;
import org.isdp.vertx.gateway.circuit.CircuitBreakerHandler;
import org.isdp.vertx.gateway.enmu.ServiceEnum;
import org.isdp.vertx.gateway.enmu.ServiceRegister;
import org.isdp.vertx.gateway.httpendpoiot.HttpEndpointOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GatewayService implements IsdpResponseWrapper {


    private Logger logger = LoggerFactory.getLogger(GatewayService.class);
    private CircuitBreakerHandler circuitBreakerHandler;
    private Vertx vertx;

    private String apiVersion = "/v1";



    public static GatewayService create(Vertx vertx,Router router){
        return  new GatewayService(vertx,router);
    }

    public GatewayService(Vertx vertx,Router router) {
        this.vertx = vertx;

        circuitBreakerHandler = CircuitBreakerHandler.getInstance("gateway-circuit-breaker", vertx);
        httpEndpoints(vertx, router);
    }

    /**
     * 接受服务总入口
     */
    public void httpEndpoints(Vertx vertx, Router router) {
        // 配置针对http请求的网关处理器
        router.route().handler(BodyHandler.create());
        //拦截所有请求，实现异步日志等操作
        router.route().handler(this::logAllQequest);

        // 添加jwt认证校验
        //        3、对符合要求的路径加jwt校验
        JWTAuth jwt= JWTAuth.create(vertx, new JWTAuthOptions());
//        router.route(apiVersion+"/*").handler(JWTAuthHandler.create(jwt));
//        router.route(apiVersion+"/*").handler(this::initNotAuth);

        router.route(apiVersion+"/*").handler(this::serviceHandler);
        // 注册指标Handler
        router.get("/hystrix-metrics").handler(HystrixMetricHandler.create(vertx));

    }

    private void logAllQequest(RoutingContext routingContext) {
        logger.info("current quest is {}",routingContext.request().uri());
        routingContext.next();
    }

    private void serviceHandler(RoutingContext routingContext) {
        //获取rest调用服务内容
        HttpEndpointOptions httpEndpointOptions = HttpEndpointOptions.create(routingContext);

        //获取gateway-name
        String[] uris = httpEndpointOptions.getUri().split("/");

        if (uris.length < 1) routingContext.response().end("resource not found!");
        // 分发请求
        dispacheService(httpEndpointOptions, routingContext);


    }

    public void dispacheService(HttpEndpointOptions httpEndpointOptions, RoutingContext routingContext) {
        //获取gateway-name
        String[] uris = httpEndpointOptions.getUri().split("/");


        String gatewayName = uris[2];


        ServiceRegister.discovery.getRecord(record1 -> gatewayName.equalsIgnoreCase(record1.getName()))
                .onSuccess(rs-> this.excuteHttpClient(rs,httpEndpointOptions,gatewayName,routingContext))
                .onFailure(rs->error(routingContext,rs));
    }

    private void excuteHttpClient(Record record,HttpEndpointOptions httpEndpointOptions,String gatewayName,RoutingContext routingContext) {
        ServiceReference httpclientServiceReference = ServiceRegister.discovery.getReference(record);
        // 获取 需要代理的httpclient元数据
//        HttpClient client = httpclientServiceReference.getAs(HttpClient.class);
        String uri = httpEndpointOptions.getUri().substring(apiVersion.length()+gatewayName.length() + 1);
        // 获取服务对象
        WebClient client = httpclientServiceReference.getAs(WebClient.class);
        circuitBreakerHandler.getBreaker()
                .execute(promise -> {
                    // 在断路器中执行的代码
                    // 这里的代码可以成功或者失败，
                    // 如果该 promise 在这里被标记为失败，断路器将自增失败数
                    // 你需要提供完整的path
                    HttpRequest<Buffer> httpRequest =  client.request(httpEndpointOptions.getMethod(), uri);
                    httpRequest.putHeaders(httpEndpointOptions.getHeaders());
                    Future<HttpResponse<Buffer>>  httpResponseFuture = null;
                    if(routingContext.getBodyAsJson()!=null){
                        httpResponseFuture=  httpRequest.sendBuffer(routingContext.getBodyAsJson().toBuffer());
                    }else{
                        httpResponseFuture=httpRequest.send();
                    }
                    httpResponseFuture.onSuccess(httpResponse->dispachHttpClientSuccess(routingContext,httpResponse));

                    httpResponseFuture.onFailure(httpResponse->dispachHttpClientError(routingContext,httpResponse));
                    httpResponseFuture.onComplete(s ->  {
                        if(s.succeeded())promise.complete(s.result());
                        if(s.failed())promise.complete(s.cause());
                        httpclientServiceReference.release();
                    });

                }).onComplete(ar -> {
                    // 处理结果.
                    if(ar.failed()){
                        System.out.println("sss"); ar.cause().printStackTrace();
                    }
                    if(ar.succeeded()){
                        logger.info(ar.result().toString());
                    }

                });
    }

    private void dispachHttpClientError(RoutingContext routingContext, Throwable throwable) {
        error(routingContext, throwable);

    }

    private void dispachHttpClientSuccess(RoutingContext routingContext, HttpResponse<Buffer> httpResponse) {
        if (httpResponse.statusCode() == 401)
            Unauthorized(routingContext, new RuntimeException(httpResponse.statusMessage()));
        if (httpResponse.statusCode() == 400)
            error(routingContext, new RuntimeException(httpResponse.statusMessage()));
        if (httpResponse.statusCode() == 500)
            error(routingContext, new RuntimeException(httpResponse.statusMessage()));
        if (httpResponse.statusCode() == 200)

            successfulled(routingContext, httpResponse.bodyAsJsonObject().getValue("data"));
    }

    /**
     * 1、处理http endpoionts方式的服务
     *
     * @param serviceEnum
     */
    public void dispacheService(ServiceEnum serviceEnum) {

    }

    private void initNotAuth(RoutingContext ctx){
        // 配置未认证请求
//        String token = ctx.user().principal().getString("token");
//        校验token
        // 这里的值为true
        boolean isAuthenticated = ctx.user() != null;
        if(isAuthenticated){
            ctx.next();
        }else{
            error(ctx,new RuntimeException("未认证"));
        }

    }
}
