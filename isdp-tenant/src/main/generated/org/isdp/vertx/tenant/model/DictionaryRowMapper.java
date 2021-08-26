package org.isdp.vertx.tenant.model;

/**
 * Mapper for {@link Dictionary}.
 * NOTE: This class has been automatically generated from the {@link Dictionary} original class using Vert.x codegen.
 */
@io.vertx.codegen.annotations.VertxGen
public interface DictionaryRowMapper extends io.vertx.sqlclient.templates.RowMapper<Dictionary> {

  @io.vertx.codegen.annotations.GenIgnore
  DictionaryRowMapper INSTANCE = new DictionaryRowMapper() { };

  @io.vertx.codegen.annotations.GenIgnore
  java.util.stream.Collector<io.vertx.sqlclient.Row, ?, java.util.List<Dictionary>> COLLECTOR = java.util.stream.Collectors.mapping(INSTANCE::map, java.util.stream.Collectors.toList());

  @io.vertx.codegen.annotations.GenIgnore
  default Dictionary map(io.vertx.sqlclient.Row row) {
    Dictionary obj = new Dictionary();
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
