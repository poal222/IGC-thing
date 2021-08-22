package org.isdp.vertx.tenant.model;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.impl.JsonUtil;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

/**
 * Converter and mapper for {@link org.isdp.vertx.tenant.model.Tenant}.
 * NOTE: This class has been automatically generated from the {@link org.isdp.vertx.tenant.model.Tenant} original class using Vert.x codegen.
 */
public class TenantConverter {


  public static void fromJson(Iterable<java.util.Map.Entry<String, Object>> json, Tenant obj) {
    for (java.util.Map.Entry<String, Object> member : json) {
      switch (member.getKey()) {
        case "code":
          if (member.getValue() instanceof String) {
            obj.setCode((String)member.getValue());
          }
          break;
        case "expired":
          if (member.getValue() instanceof String) {
            obj.setExpired((String)member.getValue());
          }
          break;
        case "name":
          if (member.getValue() instanceof String) {
            obj.setName((String)member.getValue());
          }
          break;
        case "type":
          if (member.getValue() instanceof String) {
            obj.setType((String)member.getValue());
          }
          break;
      }
    }
  }

  public static void toJson(Tenant obj, JsonObject json) {
    toJson(obj, json.getMap());
  }

  public static void toJson(Tenant obj, java.util.Map<String, Object> json) {
    if (obj.getCode() != null) {
      json.put("code", obj.getCode());
    }
    if (obj.getExpired() != null) {
      json.put("expired", obj.getExpired());
    }
    if (obj.getName() != null) {
      json.put("name", obj.getName());
    }
    if (obj.getType() != null) {
      json.put("type", obj.getType());
    }
  }
}
