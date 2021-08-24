package org.isdp.vertx.gateway.httpendpoiot;

import io.vertx.core.Future;
import io.vertx.core.MultiMap;
import io.vertx.core.buffer.Buffer;
import io.vertx.ext.web.RoutingContext;

public class HttpEndpointOptions {

    private String method = "GET";

    private String uri = "";

    private String path = "";

    private String query = "";

    private String host = "";

    private MultiMap headers = null;

    private MultiMap params = null;

    private String absoluteURI = null;

    private Future<Buffer> body = null;


    public static void create(RoutingContext routingContext) {

//        routingContext.request().
    }
}
