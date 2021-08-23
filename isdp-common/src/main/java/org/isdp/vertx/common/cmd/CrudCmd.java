package org.isdp.vertx.common.cmd;

import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Route;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;
import io.vertx.sqlclient.RowSet;
import io.vertx.sqlclient.templates.RowMapper;
import org.isdp.vertx.common.model.BaseModel;
import org.isdp.vertx.common.service.CrudService;
import org.isdp.vertx.datasource.ddl.Pager;

import java.util.*;

/**
 * crud cmd类，简化crud操作
 */
public abstract class CrudCmd<T extends CrudService,D extends BaseModel> extends BaseCmd implements CrudCmdWrapper{
    @Override
    protected void initCmd(Vertx vertx, Router router) {
        setRouter(router);
        setVertx(vertx);
        initRouteError(router);
        initCrudRoute(router);
    }

}
