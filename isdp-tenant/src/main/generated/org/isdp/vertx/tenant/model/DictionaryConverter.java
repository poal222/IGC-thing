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
      }
    }
  }

  public static void toJson(Dictionary obj, JsonObject json) {
    toJson(obj, json.getMap());
  }

  public static void toJson(Dictionary obj, java.util.Map<String, Object> json) {
  }
}
