/*
 * Copyright (c) 2021. 四川高诚物联网科技有限公司.  All rights reserved.
 * 本软件基于Apache License 2.0
 * 任何单位及个人可以用于修改，分发及商业目的
 */

package org.isdp.vertx.datasource;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.core.tracing.TracingPolicy;
import io.vertx.ext.mongo.MongoClient;
import io.vertx.mysqlclient.MySQLConnectOptions;
import io.vertx.mysqlclient.MySQLPool;
import io.vertx.pgclient.PgConnectOptions;
import io.vertx.pgclient.PgPool;
import io.vertx.sqlclient.Pool;
import io.vertx.sqlclient.PoolOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 数据库服务接口类，统一提供数据源服务
 */
public  class DataSourceProvider {
  private static Logger logger  = LoggerFactory.getLogger(DataSourceProvider.class);

  public static Pool sqlClient= null;

  public static MongoClient mongoClient= null;


   public  void init(Vertx vertx, String type, JsonObject config){
        if("Mysql".equalsIgnoreCase(type)){
          sqlClient = MySQLPool.pool(vertx, getMysqlOptions(config),getpoolOptions(config));
        }
       if("PostgreSQL".equalsIgnoreCase(type)){
         sqlClient = PgPool.pool(vertx,getPostgreSQLOptions(config),getpoolOptions(config));
       }
       if("MongoDB".equalsIgnoreCase(type)){
          mongoClient  = MongoClient.createShared(vertx, config);
       }
       if(logger.isInfoEnabled()){
         logger.info("DataSource is {} init successed",type);
       }
    }

  private PgConnectOptions getPostgreSQLOptions(JsonObject config) {
    return new PgConnectOptions(config);
  }

  private MySQLConnectOptions getMysqlOptions(JsonObject config) {
       MySQLConnectOptions mySQLConnectOptions =  new MySQLConnectOptions(config);
      mySQLConnectOptions.setTracingPolicy(TracingPolicy.ALWAYS);
      return mySQLConnectOptions;
  }
  private PoolOptions getpoolOptions(JsonObject config) {
   return new PoolOptions(config);
  }
}
