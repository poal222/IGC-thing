/*
 * Copyright (c) 2021. 四川高诚物联网科技有限公司.  All rights reserved.
 * 本软件基于Apache License 2.0
 * 任何单位及个人可以用于修改，分发及商业目的
 */

package org.isdp.vertx.common.dao;

import io.vertx.core.Future;
import io.vertx.sqlclient.SqlClient;
import io.vertx.sqlclient.SqlResult;
import org.isdp.vertx.cache.Cache;
import org.isdp.vertx.cache.CacheProvider;
import org.isdp.vertx.datasource.ddl.Pager;

import java.util.Map;
import java.util.UUID;

/**
 * 缓存支持，提供对查询结果集的缓存
 * 规则如下：
 * 1、所有的物理操作，回把对应的缓存删除
 * 2、所有的查询优先查询缓存，如果未命中，则查询实际物理表
 * 3、命名方式：session+uuid+操作名
 */
public class CrudCacheDao extends CrudDao {
  // 加载缓存
  Cache cache = CacheProvider.cache;

  // 缓存名称
  private String cacheBaseName = String.valueOf(UUID.fromString("sessionId").hashCode());

  public CrudCacheDao(SqlClient sqlClient) {
    super(sqlClient);
  }
  // 分页查询

  @Override
  public <T> Future<Pager> queryPager(SqlClient client, Map<String, Object> parameters, String querySql) {
    // 有限查询 缓存
    Future<Pager> pagerFuture = (Future<Pager>) cache.get(cacheBaseName);
    if(pagerFuture != null){
      return pagerFuture;
    }
    return super.queryPager(client, parameters, querySql);
  }
  // 普通查询 不走缓存
  // 物理操作

  @Override
  public Future<SqlResult<Void>> update(SqlClient sqlClient, Map<String, Object> param, String sql) {
    // 删除缓存
    cache.remove(cacheBaseName);
    return super.update(sqlClient, param, sql);
  }


}
