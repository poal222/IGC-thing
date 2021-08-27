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
        case "code":
          if (member.getValue() instanceof String) {
            obj.setCode((String)member.getValue());
          }
          break;
        case "dicID":
          if (member.getValue() instanceof String) {
            obj.setDicID((String)member.getValue());
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
      }
    }
  }

  public static void toJson(DictionaryItem obj, JsonObject json) {
    toJson(obj, json.getMap());
  }

  public static void toJson(DictionaryItem obj, java.util.Map<String, Object> json) {
    if (obj.getCode() != null) {
      json.put("code", obj.getCode());
    }
    if (obj.getDicID() != null) {
      json.put("dicID", obj.getDicID());
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
  }
}
