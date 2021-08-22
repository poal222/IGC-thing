package org.isdp.vertx.common.cmd;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.sqlclient.templates.RowMapper;

public abstract class WebCmd<T> {

    private Vertx vertx;
    private Router router;
    private RowMapper<T> rowMapper;
    private String webRoot = "";


    public WebCmd(Vertx vertx, Router router) {
        this.vertx = vertx;
        this.router = router;
    }

    public String getWebRoot() {
        return webRoot;
    }

    public void setWebRoot(String webRoot) {
        this.webRoot = webRoot;
    }

    public Vertx getVertx() {
        return vertx;
    }

    public void setVertx(Vertx vertx) {
        this.vertx = vertx;
    }

    public Router getRouter() {
        return router;
    }

    public void setRouter(Router router) {
        this.router = router;
    }

    public RowMapper<T> getRowMapper() {
        return rowMapper;
    }

    public void setRowMapper(RowMapper<T> rowMapper) {
        this.rowMapper = rowMapper;
    }

    public abstract void init();
}
