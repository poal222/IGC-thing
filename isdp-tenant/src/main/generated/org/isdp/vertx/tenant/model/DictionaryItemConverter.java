package org.isdp.vertx.tenant.model;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.impl.JsonUtil;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

/**
 * Converter and mapper for {@link org.isdp.vertx.tenant.model.DictionaryItem}.
 * NOTE: This class has been automatically generated from the {@link org.isdp.vertx.tenant.model.DictionaryItem} original class using Vert.x codegen.
 */
public class DictionaryItemConverter {


  public static void fromJson(Iterable<java.util.Map.Entry<String, Object>> json, DictionaryItem obj) {
    for (java.util.Map.Entry<String, Object> member : json) {
      switch (member.getKey()) {
        case "children":
          if (member.getValue() instanceof JsonArray) {
            java.util.ArrayList<io.vertx.core.json.JsonObject> list =  new java.util.ArrayList<>();
            ((Iterable<Object>)member.getValue()).forEach( item -> {
              if (item instanceof JsonObject)
                list.add(((JsonObject)item).copy());
            });
            obj.setChildren(list);
          }
          break;
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
        case "dicId":
          if (member.getValue() instanceof String) {
            obj.setDicId((String)member.getValue());
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
        case "parentID":
          if (member.getValue() instanceof String) {
            obj.setParentID((String)member.getValue());
          }
          break;
        case "path":
          if (member.getValue() instanceof String) {
            obj.setPath((String)member.getValue());
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

  public static void toJson(DictionaryItem obj, JsonObject json) {
    toJson(obj, json.getMap());
  }

  public static void toJson(DictionaryItem obj, java.util.Map<String, Object> json) {
    if (obj.getChildren() != null) {
      JsonArray array = new JsonArray();
      obj.getChildren().forEach(item -> array.add(item));
      json.put("children", array);
    }
    if (obj.getCode() != null) {
      json.put("code", obj.getCode());
    }
    if (obj.getCreateTime() != null) {
      json.put("createTime", obj.getCreateTime());
    }
    if (obj.getDicId() != null) {
      json.put("dicId", obj.getDicId());
    }
    if (obj.getId() != null) {
      json.put("id", obj.getId());
    }
    if (obj.getName() != null) {
      json.put("name", obj.getName());
    }
    if (obj.getParentID() != null) {
      json.put("parentID", obj.getParentID());
    }
    if (obj.getPath() != null) {
      json.put("path", obj.getPath());
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
