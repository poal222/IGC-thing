package org.isdp.vertx.tenant.model;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.impl.JsonUtil;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

/**
 * Converter and mapper for {@link org.isdp.vertx.tenant.model.Dictionary}.
 * NOTE: This class has been automatically generated from the {@link org.isdp.vertx.tenant.model.Dictionary} original class using Vert.x codegen.
 */
public class DictionaryConverter {


  public static void fromJson(Iterable<java.util.Map.Entry<String, Object>> json, Dictionary obj) {
    for (java.util.Map.Entry<String, Object> member : json) {
      switch (member.getKey()) {
        case "code":
          if (member.getValue() instanceof String) {
            obj.setCode((String)member.getValue());
          }
          break;
        case "createTime":
          if (member.getValue() instanceof String) {
            obj.setCreateTime((String)member.getValue());
          }
          break;
        case "id":
          if (member.getValue() instanceof String) {
            obj.setId((String)member.getValue());
          }
          break;
        case "name":
          if (member.getValue() instanceof String) {
            obj.setName((String)member.getValue());
          }
          break;
        case "seq":
          if (member.getValue() instanceof Number) {
            obj.setSeq(((Number)member.getValue()).intValue());
          }
          break;
        case "tenantId":
          if (member.getValue() instanceof String) {
            obj.setTenantId((String)member.getValue());
          }
          break;
        case "updateTime":
          if (member.getValue() instanceof String) {
            obj.setUpdateTime((String)member.getValue());
          }
          break;
      }
    }
  }

  public static void toJson(Dictionary obj, JsonObject json) {
    toJson(obj, json.getMap());
  }

  public static void toJson(Dictionary obj, java.util.Map<String, Object> json) {
    if (obj.getCode() != null) {
      json.put("code", obj.getCode());
    }
    if (obj.getCreateTime() != null) {
      json.put("createTime", obj.getCreateTime());
    }
    if (obj.getId() != null) {
      json.put("id", obj.getId());
    }
    if (obj.getName() != null) {
      json.put("name", obj.getName());
    }
    if (obj.getSeq() != null) {
      json.put("seq", obj.getSeq());
    }
    if (obj.getTenantId() != null) {
      json.put("tenantId", obj.getTenantId());
    }
    if (obj.getUpdateTime() != null) {
      json.put("updateTime", obj.getUpdateTime());
    }
  }
}
