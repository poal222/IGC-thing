package org.isdp.vertx.common.cmd;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.sqlclient.templates.RowMapper;

public  interface WebCmd {


    public String getRoutePath();
}
