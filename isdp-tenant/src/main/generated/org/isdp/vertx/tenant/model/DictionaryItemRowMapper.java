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
    if ((idx = row.getColumnIndex("children")) != -1 && (val = row.getArrayOfJsonObjects(idx)) != null) {
      obj.setChildren(java.util.Arrays.stream((io.vertx.core.json.JsonObject[])val).map(elt -> elt).collect(java.util.stream.Collectors.toCollection(java.util.ArrayList::new)));
    }
    if ((idx = row.getColumnIndex("code")) != -1 && (val = row.getString(idx)) != null) {
      obj.setCode((java.lang.String)val);
    }
    if ((idx = row.getColumnIndex("createTime")) != -1 && (val = row.getString(idx)) != null) {
      obj.setCreateTime((java.lang.String)val);
    }
    if ((idx = row.getColumnIndex("dic_id")) != -1 && (val = row.getString(idx)) != null) {
      obj.setDicId((java.lang.String)val);
    }
    if ((idx = row.getColumnIndex("id")) != -1 && (val = row.getString(idx)) != null) {
      obj.setId((java.lang.String)val);
    }
    if ((idx = row.getColumnIndex("name")) != -1 && (val = row.getString(idx)) != null) {
      obj.setName((java.lang.String)val);
    }
    if ((idx = row.getColumnIndex("parent_id")) != -1 && (val = row.getString(idx)) != null) {
      obj.setParentID((java.lang.String)val);
    }
    if ((idx = row.getColumnIndex("path")) != -1 && (val = row.getString(idx)) != null) {
      obj.setPath((java.lang.String)val);
    }
    if ((idx = row.getColumnIndex("seq")) != -1 && (val = row.getInteger(idx)) != null) {
      obj.setSeq((java.lang.Integer)val);
    }
    if ((idx = row.getColumnIndex("tenantId")) != -1 && (val = row.getString(idx)) != null) {
      obj.setTenantId((java.lang.String)val);
    }
    if ((idx = row.getColumnIndex("updateTime")) != -1 && (val = row.getString(idx)) != null) {
      obj.setUpdateTime((java.lang.String)val);
    }
    return obj;
  }
}
