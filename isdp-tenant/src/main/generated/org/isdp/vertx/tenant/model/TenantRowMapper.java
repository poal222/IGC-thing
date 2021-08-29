package org.isdp.vertx.tenant.model;

/**
 * Mapper for {@link Tenant}.
 * NOTE: This class has been automatically generated from the {@link Tenant} original class using Vert.x codegen.
 */
@io.vertx.codegen.annotations.VertxGen
public interface TenantRowMapper extends io.vertx.sqlclient.templates.RowMapper<Tenant> {

  @io.vertx.codegen.annotations.GenIgnore
  TenantRowMapper INSTANCE = new TenantRowMapper() { };

  @io.vertx.codegen.annotations.GenIgnore
  java.util.stream.Collector<io.vertx.sqlclient.Row, ?, java.util.List<Tenant>> COLLECTOR = java.util.stream.Collectors.mapping(INSTANCE::map, java.util.stream.Collectors.toList());

  @io.vertx.codegen.annotations.GenIgnore
  default Tenant map(io.vertx.sqlclient.Row row) {
    Tenant obj = new Tenant();
    Object val;
    int idx;
    if ((idx = row.getColumnIndex("code")) != -1 && (val = row.getString(idx)) != null) {
      obj.setCode((java.lang.String)val);
    }
    if ((idx = row.getColumnIndex("createTime")) != -1 && (val = row.getString(idx)) != null) {
      obj.setCreateTime((java.lang.String)val);
    }
    if ((idx = row.getColumnIndex("expired")) != -1 && (val = row.getString(idx)) != null) {
      obj.setExpired((java.lang.String)val);
    }
    if ((idx = row.getColumnIndex("id")) != -1 && (val = row.getString(idx)) != null) {
      obj.setId((java.lang.String)val);
    }
    if ((idx = row.getColumnIndex("name")) != -1 && (val = row.getString(idx)) != null) {
      obj.setName((java.lang.String)val);
    }
    if ((idx = row.getColumnIndex("tenantId")) != -1 && (val = row.getString(idx)) != null) {
      obj.setTenantId((java.lang.String)val);
    }
    if ((idx = row.getColumnIndex("type")) != -1 && (val = row.getString(idx)) != null) {
      obj.setType((java.lang.String)val);
    }
    if ((idx = row.getColumnIndex("updateTime")) != -1 && (val = row.getString(idx)) != null) {
      obj.setUpdateTime((java.lang.String)val);
    }
    return obj;
  }
}
