package org.isdp.vertx.datasource.ddl;

import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.sqlclient.Row;
import io.vertx.sqlclient.RowSet;
import io.vertx.sqlclient.SqlClient;
import io.vertx.sqlclient.SqlResult;
import io.vertx.sqlclient.templates.RowMapper;
import io.vertx.sqlclient.templates.SqlTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface SqlOperaction {

  /**
   * 全查询
   * @param client
   * @param parameters
   * @param t 映射的对象
   * @param querySql
   * @param <T>
   * @return
   */
  default   <T> Future<RowSet<T>> queryAll(SqlClient client, Map<String, Object> parameters , RowMapper<T> t,String querySql){

    Promise promise=Promise.promise();
    // 查询条件
    StringBuilder sb = new StringBuilder(querySql);
    return SqlTemplate
      .forQuery(client, sb.toString())
      .mapTo(t)
      .execute(parameters);
  }

  /**
   * 分页查询
   * @param client
   * @param parameters 查询参数current和pageSize 必须要有
   * @param querySql 查询Sql
   * @param <T>
   * @return
   */
  default   <T> Future<Pager> queryPager(SqlClient client, Map<String, Object> parameters,String querySql ){
    Promise promise=Promise.promise();
    Pager pager = new Pager();
    pager.setCurrent((Integer) parameters.get("current"));
    pager.setPageSize((Integer) parameters.get("pageSize"));
    List dataList =new ArrayList<>();
    pager.setList(dataList);
    pager.setTotal(0);
    StringBuilder sb = new StringBuilder(querySql);
    sb.append("  LIMIT #{current}, #{pageSize} ");
    SqlTemplate
      .forQuery(client, sb.toString())
      .mapTo(Row::toJson)
      .execute(parameters)
      .onSuccess(rowset ->{
        rowset.iterator().forEachRemaining(row ->{
          dataList.add(row);
        });
        pager.setList(dataList);
      })
      .compose(res -> client
        // 查询总数
        .query("select count(*) as total from (" + querySql + ") T")
        .execute()
        .onSuccess(res1 -> {
          for (Row row : res1) {
            pager.setTotal(row.getInteger("total"));
          }
          promise.complete(pager);
        }));
//    promise.complete(pager);
    return promise.future();
  }
  /**
   * 普通查询
   * @param client
   * @param parameters
   * @param t 映射的对象
   * @param querySql
   * @param <T>
   * @return
   */
  default   <T> Future<RowSet<T>> query(SqlClient client, Map<String, Object> parameters , RowMapper<T> t,String querySql){

    Promise promise=Promise.promise();
    // 查询条件
    StringBuilder sb = new StringBuilder(querySql);
    return SqlTemplate
      .forQuery(client, sb.toString())
      .mapTo(t)
      .execute(parameters);
  }
  /**
   * 物理操作执行器 ，单Sql操作，不涉及 事务管理，建议事务管理放在service层使用
   * @param sqlClient
   * @param param
   * @param sql
   * @return
   */
  default Future<SqlResult<Void>> update(SqlClient sqlClient, Map<String, Object> param,String sql) {
    return  SqlTemplate
      .forUpdate(sqlClient, sql)
      .execute(param);
  }
}
