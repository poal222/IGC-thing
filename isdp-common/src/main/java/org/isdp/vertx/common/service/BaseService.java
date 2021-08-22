/*
 * Copyright (c) 2021. 四川高诚物联网科技有限公司.  All rights reserved.
 * 本软件基于Apache License 2.0
 * 任何单位及个人可以用于修改，分发及商业目的
 */

package org.isdp.vertx.common.service;

import io.vertx.core.Future;
import io.vertx.sqlclient.Pool;
import io.vertx.sqlclient.SqlConnection;
import org.isdp.vertx.datasource.DataSourceProvider;

/**
 * 提供事务和业务处理层逻辑
 */
public class BaseService {
  /**
   *   导入 sqlpool
   */
  protected   Pool sqlPool=  DataSourceProvider.sqlClient;


  /**
   * 提供简单事务
   * @return
   */
   Future<SqlConnection> getTransaction(){
     return sqlPool.withTransaction(sqlConnection -> Future.succeededFuture(sqlConnection));
   }



}
