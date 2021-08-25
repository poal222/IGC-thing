package org.isdp.vertx.common.cmd;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.RoutingContext;
import org.isdp.vertx.common.model.IsdpResponses;
import org.isdp.vertx.datasource.ddl.Pager;


/**
 * 提供统一消息返回机制
 * 返回机制如下：
 * {
 * "success": true,
 * "data": {},
 * "errorCode": "1001",
 * "errorMessage": "error message",
 * "showType": 2,
 * "traceId": "someid",
 * "host": "10.1.1.1"
 * }
 */
public interface IsdpResponseWrapper {
    // 发送信息
    default void interServerError(RoutingContext routingContext, Throwable throwable) {
        IsdpResponses isdpResponses = new IsdpResponses();
        isdpResponses.setSuccess(true);
        isdpResponses.setHost(routingContext.request().host());
        isdpResponses.setErrorCode("500");
        isdpResponses.setShowType("2");
        isdpResponses.setErrorMessage(throwable.getMessage());

        routingContext.response()
                .setStatusCode(500)
                .end(isdpResponses.toJson().encodePrettily());
    }
    // 发送信息
    default void Unauthorized(RoutingContext routingContext, Throwable throwable) {
        IsdpResponses isdpResponses = new IsdpResponses();
        isdpResponses.setSuccess(true);
        isdpResponses.setHost(routingContext.request().host());
        isdpResponses.setErrorCode("401");
        isdpResponses.setShowType("2");
        isdpResponses.setErrorMessage(throwable.getMessage());

        routingContext.response()
                .setStatusCode(400)
                .end(isdpResponses.toJson().encodePrettily());
    }
    // 发送信息
    default void error(RoutingContext routingContext, Throwable throwable) {
        IsdpResponses isdpResponses = new IsdpResponses();
        isdpResponses.setSuccess(true);
        isdpResponses.setHost(routingContext.request().host());
        isdpResponses.setErrorCode("400");
        isdpResponses.setShowType("2");
        isdpResponses.setErrorMessage(throwable.getMessage());

        routingContext.response()
                .setStatusCode(400)
                .end(isdpResponses.toJson().encodePrettily());
    }
    default void successfulled(RoutingContext routingContext, String  message, Object data) {
        IsdpResponses isdpResponses = new IsdpResponses();
        isdpResponses.setSuccess(true);
        isdpResponses.setHost(routingContext.request().host());
        isdpResponses.setSuccessMessage(message);
        isdpResponses.setShowType("2");
        routingContext.response()
                .setStatusCode(200)
                .end(isdpResponses.toJson().put("data",data).encodePrettily());
    }
    default void successfulled(RoutingContext routingContext, String  message) {
        successfulled(routingContext,"Success",message);
    }
    default void successfulled(RoutingContext routingContext, Pager pager) {
        successfulled(routingContext,"Success",pager.toJson());
    }
    default void successfulled(RoutingContext routingContext, Object data) {
        successfulled(routingContext,"Success",data);
    }
}
