/*
 * Copyright (c) 2021. 四川高诚物联网科技有限公司.  All rights reserved.
 * 本软件基于Apache License 2.0
 * 任何单位及个人可以用于修改，分发及商业目的
 */

package org.isdp.vertx.common.cmd;


import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

public abstract class BaseCmd {

    private Vertx vertx   ;

    private Router router ;


    public void setVertx(Vertx vertx) {
        this.vertx = vertx;
    }

    public void setRouter(Router router) {
        this.router = router;
    }

    public Router getRouter() {
        return router;
    }

    public Vertx getVertx() {
        return vertx;
    }

    protected abstract void initCmd(Vertx vertx, Router router);






}
