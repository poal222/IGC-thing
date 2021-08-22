/*
 * Copyright (c) 2021. 四川高诚物联网科技有限公司.  All rights reserved.
 * 本软件基于Apache License 2.0
 * 任何单位及个人可以用于修改，分发及商业目的
 */

package org.isdp.vertx.common.dao;


import io.vertx.core.Future;
import io.vertx.sqlclient.SqlClient;
import io.vertx.sqlclient.templates.RowMapper;
import org.isdp.vertx.datasource.ddl.SqlOperaction;

import java.util.Collections;

public class  CrudDao<T> extends BaseDao implements SqlOperaction {
  public CrudDao(SqlClient sqlClient) {
    super(sqlClient);
  }

// 根据id查询，新增，修改，删除

  public <T> Future<T> getOneById(String sql, String id, RowMapper<T> t){
    return (Future<T>) query(sqlClient,Collections.singletonMap("id",id),t,sql);
  }

}
