package org.isdp.vertx.tenant.model;

/**
 * Mapper for {@link DictionaryItem}.
 * NOTE: This class has been automatically generated from the {@link DictionaryItem} original class using Vert.x codegen.
 */
@io.vertx.codegen.annotations.VertxGen
public interface DictionaryItemRowMapper extends io.vertx.sqlclient.templates.RowMapper<DictionaryItem> {

  @io.vertx.codegen.annotations.GenIgnore
  DictionaryItemRowMapper INSTANCE = new DictionaryItemRowMapper() { };

  @io.vertx.codegen.annotations.GenIgnore
  java.util.stream.Collector<io.vertx.sqlclient.Row, ?, java.util.List<DictionaryItem>> COLLECTOR = java.util.stream.Collectors.mapping(INSTANCE::map, java.util.stream.Collectors.toList());

  @io.vertx.codegen.annotations.GenIgnore
  default DictionaryItem map(io.vertx.sqlclient.Row row) {
    DictionaryItem obj = new DictionaryItem();
    Object val;
    int idx;
    if ((idx = row.getColumnIndex("createTime")) != -1 && (val = row.get(java.util.Date.class, idx)) != null) {
      obj.setCreateTime((java.util.Date)val);
    }
    if ((idx = row.getColumnIndex("updateTime")) != -1 && (val = row.get(java.util.Date.class, idx)) != null) {
      obj.setUpdateTime((java.util.Date)val);
    }
    return obj;
  }
}
