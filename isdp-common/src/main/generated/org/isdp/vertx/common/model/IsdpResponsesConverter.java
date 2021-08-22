package org.isdp.vertx.common.model;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.impl.JsonUtil;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

/**
 * Converter and mapper for {@link org.isdp.vertx.common.model.IsdpResponses}.
 * NOTE: This class has been automatically generated from the {@link org.isdp.vertx.common.model.IsdpResponses} original class using Vert.x codegen.
 */
public class IsdpResponsesConverter {


  public static void fromJson(Iterable<java.util.Map.Entry<String, Object>> json, IsdpResponses obj) {
    for (java.util.Map.Entry<String, Object> member : json) {
      switch (member.getKey()) {
        case "errorCode":
          if (member.getValue() instanceof String) {
            obj.setErrorCode((String)member.getValue());
          }
          break;
        case "errorMessage":
          if (member.getValue() instanceof String) {
            obj.setErrorMessage((String)member.getValue());
          }
          break;
        case "host":
          if (member.getValue() instanceof String) {
            obj.setHost((String)member.getValue());
          }
          break;
        case "showType":
          if (member.getValue() instanceof String) {
            obj.setShowType((String)member.getValue());
          }
          break;
        case "success":
          if (member.getValue() instanceof Boolean) {
            obj.setSuccess((Boolean)member.getValue());
          }
          break;
        case "successMessage":
          if (member.getValue() instanceof String) {
            obj.setSuccessMessage((String)member.getValue());
          }
          break;
        case "traceId":
          if (member.getValue() instanceof String) {
            obj.setTraceId((String)member.getValue());
          }
          break;
      }
    }
  }

  public static void toJson(IsdpResponses obj, JsonObject json) {
    toJson(obj, json.getMap());
  }

  public static void toJson(IsdpResponses obj, java.util.Map<String, Object> json) {
    if (obj.getErrorCode() != null) {
      json.put("errorCode", obj.getErrorCode());
    }
    if (obj.getErrorMessage() != null) {
      json.put("errorMessage", obj.getErrorMessage());
    }
    if (obj.getHost() != null) {
      json.put("host", obj.getHost());
    }
    if (obj.getShowType() != null) {
      json.put("showType", obj.getShowType());
    }
    if (obj.getSuccess() != null) {
      json.put("success", obj.getSuccess());
    }
    if (obj.getSuccessMessage() != null) {
      json.put("successMessage", obj.getSuccessMessage());
    }
    if (obj.getTraceId() != null) {
      json.put("traceId", obj.getTraceId());
    }
  }
}
