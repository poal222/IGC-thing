package org.isdp.vertx.common.model;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

import java.io.Serializable;

/**
 * 统一返回值
 * * 返回机制如下：
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
@DataObject(generateConverter=true)
public class IsdpResponses implements Serializable {

    private Boolean success;
    private Object data;
    private String successMessage;

    private String errorCode;
    private String errorMessage;
    private String showType;
    private String traceId;
    private String host;



    public String getSuccessMessage() {
        return successMessage;
    }

    public void setSuccessMessage(String successMessage) {
        this.successMessage = successMessage;
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public String getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public void setErrorMessage(String errorMessage) {
        this.errorMessage = errorMessage;
    }

    public String getShowType() {
        return showType;
    }

    public void setShowType(String showType) {
        this.showType = showType;
    }

    public String getTraceId() {
        return traceId;
    }

    public void setTraceId(String traceId) {
        this.traceId = traceId;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

//
    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        IsdpResponsesConverter.toJson(this, json);
        return json;
    }
}
