package org.isdp.vertx.tenant.service;

import io.vertx.core.Future;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.auth.User;
import io.vertx.ext.auth.authentication.AuthenticationProvider;
import io.vertx.ext.auth.sqlclient.SqlAuthentication;
import io.vertx.ext.auth.sqlclient.SqlAuthenticationOptions;
import io.vertx.sqlclient.SqlClient;


public class AuthSqlClientService {

  private static AuthenticationProvider authenticationProvider = null;

  private AuthSqlClientService(SqlClient sqlClient) {
    SqlAuthenticationOptions options = new SqlAuthenticationOptions();
    /**
     * 认证查询器
     */
    options.setAuthenticationQuery("SELECT password FROM users WHERE username = ?");
    // SQL client can be any of the known implementations
    // *. Postgres
    // *. MySQL
    // *. etc...
    authenticationProvider =
      SqlAuthentication.create(sqlClient, options);
  }


  /**
   * 数据库认证提供器
   *
   * @param sqlClient
   */
  public static AuthSqlClientService create(SqlClient sqlClient) {

    return new AuthSqlClientService(sqlClient);
  }


  public Future<User> authFuture(JsonObject authInfo) {
    return  authenticationProvider.authenticate(authInfo);
  }


}
