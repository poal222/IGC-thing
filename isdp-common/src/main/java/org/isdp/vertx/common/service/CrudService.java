/*
 * Copyright (c) 2021. 四川高诚物联网科技有限公司.  All rights reserved.
 * 本软件基于Apache License 2.0
 * 任何单位及个人可以用于修改，分发及商业目的
 */

package org.isdp.vertx.common.service;

import io.vertx.core.Future;
import io.vertx.sqlclient.Pool;
import io.vertx.sqlclient.RowSet;
import io.vertx.sqlclient.SqlResult;
import io.vertx.sqlclient.templates.RowMapper;
import org.isdp.vertx.common.dao.CrudDao;
import org.isdp.vertx.datasource.DataSourceProvider;
import org.isdp.vertx.datasource.ddl.Pager;

import java.util.Map;

/**
 * 提供事务和业务处理层逻辑
 */
public class  CrudService<T> extends BaseService{


  //默认数据源
  private Pool pool =null;

  public void setPool(Pool pool) {
    this.pool = pool;
  }

  private String updateSql="";
  private String delteSql = "";
  private String insertSql = "";
  private String querySql = "";
  private String queryPagerSql = "";
  private String detailSql="";

  public void setDetailSql(String detailSql) {
    this.detailSql = detailSql;
  }

  public void setUpdateSql(String updateSql) {
    this.updateSql = updateSql;
  }

  public void setDelteSql(String delteSql) {
    this.delteSql = delteSql;
  }

  public void setInsertSql(String insertSql) {
    this.insertSql = insertSql;
  }

  public void setQuerySql(String querySql) {
    this.querySql = querySql;
  }

  public void setQueryPagerSql(String queryPagerSql) {
    this.queryPagerSql = queryPagerSql;
  }

  private CrudDao crudDao;

  public void setCrudDao(CrudDao crudDao) {
    this.crudDao = crudDao;
  }

  public Future<SqlResult<Void>> update(Map<String,Object> map) {
   return crudDao.update(pool,map,updateSql);
  }

  public Future<SqlResult<Void>> insert(Map<String,Object> map) {

    return crudDao.update(pool,map,insertSql);
  }

  public Future<SqlResult<Void>> delete(Map<String,Object> map) {
    return crudDao.update(pool,map,delteSql);
  }

  public Future<SqlResult<Void>> detail(String id, RowMapper<T> rowMapper) {
    return crudDao.getOneById(detailSql,id,rowMapper);
  }

  public Future<RowSet<T>> query(Map<String,Object> map, RowMapper<T> rowMapper) {
    return crudDao.query(pool,map,rowMapper,querySql);
  }
  public Future<Pager> queryPager(Map<String,Object> map) {
    return crudDao.queryPager(pool,map,queryPagerSql);
  }

}
