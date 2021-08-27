package org.isdp.vertx.common.cmd;

import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.sqlclient.RowSet;
import io.vertx.sqlclient.templates.RowMapper;
import org.isdp.vertx.common.model.BaseModel;
import org.isdp.vertx.common.service.CrudService;
import org.isdp.vertx.datasource.ddl.Pager;

import java.time.LocalDate;
import java.util.*;

/**
 * crud 通用cmd接口，抽象出来实现crud的动态复用
 */
public interface CrudCmdWrapper<T extends CrudService, R extends RowMapper> extends IsdpResponseWrapper,WebCmd{

    /**
     * 定义curdservice 实现类的加载
     * @return
     */
    public T getCrudService();
    /**
     * 定义RowMapper 实现行转换器的加载
     * @return
     */
    public R getRowMapper();


    default void initRouteError(Router router) {
        router.errorHandler(400, routingContext -> {
            error(routingContext, routingContext.failure());
        });
        router.errorHandler(500, routingContext -> {
            interServerError(routingContext, new RuntimeException("internet Server Error！"));
        });
        router.errorHandler(404, routingContext -> {
            error(routingContext, new RuntimeException("resource is not found！"));
        });
        router.errorHandler(405, routingContext -> {
            error(routingContext, new RuntimeException("method is not allowed！"));
        });
    }

    default Router initCrudRoute(Router router) {

        router.post(getRoutePath()+"/_query").handler(this::query);
        router.post(getRoutePath()+"/_queryPage").handler(this::queryPage);
        router.get(getRoutePath()+"/_detail/:id").handler(this::detail);
        router.delete(getRoutePath()+"/_delete/:id").handler(this::delete);
        router.post(getRoutePath()+"/_insert").handler(this::insert);
        router.post(getRoutePath()+"/_update").handler(this::update);
        return router;
    }

    default void queryPage(RoutingContext routingContext) {
        //查询条件
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("current", 1);
        paramMap.put("pageSize", 10);

        getCrudService().queryPager(paramMap)
                .onSuccess(pager -> {
                    Pager pager1 = (Pager) pager;
                    successfulled(routingContext, pager1);
                }).onFailure(event -> {
            System.out.println(event);
            error(routingContext, (Throwable) event);
        });
    }

    default void update(RoutingContext routingContext) {
    }

    default void insert(RoutingContext routingContext) {
        JsonObject jsonObject = routingContext.getBodyAsJson();
        Map map = new HashMap();

        jsonObject.stream().forEach(stringObjectEntry -> {
            map.put(stringObjectEntry.getKey(), stringObjectEntry.getValue());
        });


        map.put("id", UUID.randomUUID().hashCode());
        map.put("createTime",  new java.sql.Date(System.currentTimeMillis()));
        map.put("updateTime", new java.sql.Date(System.currentTimeMillis()));
        getCrudService().insert(map).onSuccess(ex -> successfulled(routingContext, "success"))
                .onFailure(ex -> error(routingContext, (Throwable) ex));

    }

    default void delete(RoutingContext routingContext) {
        String id = routingContext.pathParam("id");
        getCrudService().delete(Collections.singletonMap("id", id)).onSuccess(ex -> successfulled(routingContext, "success"))
                .onFailure(ex -> error(routingContext, (Throwable) ex));

    }

    default void detail(RoutingContext routingContext) {
        String id = routingContext.pathParam("id");
        getCrudService().detail(id, getRowMapper())
                .onSuccess(list -> {
                    RowSet rowSet = (RowSet) list;
                    rowSet.iterator().forEachRemaining(row -> {
                        BaseModel baseModel = (BaseModel) row;
                        successfulled(routingContext, baseModel.toJson());
                    });
                    successfulled(routingContext, new JsonObject());
                }).onFailure(event -> {
            System.out.println(event);
            error(routingContext, (Throwable) event);
        });
    }

    default void query(RoutingContext routingContext) {
        //查询条件
        Map<String, Object> paramMap = new HashMap<>();
        getCrudService().query(paramMap, getRowMapper())
                .onSuccess(list -> {
                    RowSet rowSet = (RowSet) list;
                    List date = new ArrayList();
                    rowSet.iterator().forEachRemaining(row -> {
                        BaseModel baseModel = (BaseModel) row;
                        date.add(baseModel.toJson());
                    });
                    successfulled(routingContext, date);
                }).onFailure(event -> {
            System.out.println(event);
            error(routingContext, (Throwable) event);
        });
    }

}
