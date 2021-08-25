package org.isdp.vertx.gateway.httpendpoiot;

import io.vertx.core.Future;
import io.vertx.core.MultiMap;
import io.vertx.core.buffer.Buffer;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.http.HttpServerRequest;
import io.vertx.ext.web.RoutingContext;

public class HttpEndpointOptions {

    private HttpMethod method = null;

    private String uri = "";

    private String path = "";

    private String query = "";

    private String host = "";

    private MultiMap headers = null;

    private MultiMap params = null;

    private String absoluteURI = null;

    private Future<Buffer> body = null;

    private HttpEndpointOptions(RoutingContext routingContext) {
        HttpServerRequest request = routingContext.request();
        this.method = request.method();
        this.uri = request.uri();
        this.absoluteURI = request.absoluteURI();
        this.query = request.query();
        this.host = request.remoteAddress().host();
        this.path = request.path();
        this.headers = request.headers();
        this.params = request.params();
//        this.body = request.body();
    }

    public HttpMethod getMethod() {
        return method;
    }

    public String getUri() {
        return uri;
    }

    public String getPath() {
        return path;
    }

    public String getQuery() {
        return query;
    }

    public String getHost() {
        return host;
    }

    public MultiMap getHeaders() {
        return headers;
    }

    public MultiMap getParams() {
        return params;
    }

    public String getAbsoluteURI() {
        return absoluteURI;
    }

    public Future<Buffer> getBody() {
        return body;
    }

    public static HttpEndpointOptions create(RoutingContext routingContext) {

           return new HttpEndpointOptions(routingContext);
    }



}
