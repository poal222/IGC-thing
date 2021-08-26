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
      }
    }
  }

  public static void toJson(DictionaryItem obj, JsonObject json) {
    toJson(obj, json.getMap());
  }

  public static void toJson(DictionaryItem obj, java.util.Map<String, Object> json) {
  }
}
