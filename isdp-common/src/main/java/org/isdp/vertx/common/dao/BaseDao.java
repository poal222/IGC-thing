/*
 * Copyright (c) 2021. 四川高诚物联网科技有限公司.  All rights reserved.
 * 本软件基于Apache License 2.0
 * 任何单位及个人可以用于修改，分发及商业目的
 */

package org.isdp.vertx.common.dao;


import io.vertx.sqlclient.SqlClient;

/**
 * 基础Dao层，ISDP框架抽象出dao层
 * 提供一种基于集成方式的dao层扩展机制
 */
public class BaseDao {
  /**
   * 引入 sqlClient
   */
  protected SqlClient sqlClient;

  public BaseDao(SqlClient sqlClient) {
    this.sqlClient = sqlClient;
  }

  public SqlClient getSqlClient() {
    return sqlClient;
  }
}
