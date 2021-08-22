package org.isdp.vertx.datasource.ddl;

import io.vertx.core.json.JsonObject;
import io.vertx.core.json.JsonArray;
import io.vertx.core.json.impl.JsonUtil;
import java.time.Instant;
import java.time.format.DateTimeFormatter;

/**
 * Converter and mapper for {@link org.isdp.vertx.datasource.ddl.Pager}.
 * NOTE: This class has been automatically generated from the {@link org.isdp.vertx.datasource.ddl.Pager} original class using Vert.x codegen.
 */
public class PagerConverter {


  public static void fromJson(Iterable<java.util.Map.Entry<String, Object>> json, Pager obj) {
    for (java.util.Map.Entry<String, Object> member : json) {
      switch (member.getKey()) {
        case "current":
          if (member.getValue() instanceof Number) {
            obj.setCurrent(((Number)member.getValue()).intValue());
          }
          break;
        case "list":
          if (member.getValue() instanceof JsonArray) {
            java.util.ArrayList<java.lang.Object> list =  new java.util.ArrayList<>();
            ((Iterable<Object>)member.getValue()).forEach( item -> {
              if (item instanceof Object)
                list.add(item);
            });
            obj.setList(list);
          }
          break;
        case "pageSize":
          if (member.getValue() instanceof Number) {
            obj.setPageSize(((Number)member.getValue()).intValue());
          }
          break;
        case "total":
          if (member.getValue() instanceof Number) {
            obj.setTotal(((Number)member.getValue()).intValue());
          }
          break;
      }
    }
  }

  public static void toJson(Pager obj, JsonObject json) {
    toJson(obj, json.getMap());
  }

  public static void toJson(Pager obj, java.util.Map<String, Object> json) {
    if (obj.getCurrent() != null) {
      json.put("current", obj.getCurrent());
    }
    if (obj.getList() != null) {
      JsonArray array = new JsonArray();
      obj.getList().forEach(item -> array.add(item));
      json.put("list", array);
    }
    if (obj.getPageSize() != null) {
      json.put("pageSize", obj.getPageSize());
    }
    if (obj.getTotal() != null) {
      json.put("total", obj.getTotal());
    }
  }
}
