package org.isdp.vertx.common.cmd;

import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;
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
public class CrudCmd<T extends CrudService,D extends BaseModel> extends BaseCmd{

    private T crudService = null;

    private RowMapper<D> rowMapper = null;
    // 统一路由
    public Router router  =null;

    public void setCrudService(T crudService) {
        this.crudService = crudService;
    }

    public T getCrudService() {
        return crudService;
    }

    public void setRowMapper(RowMapper rowMapper) {
        this.rowMapper = rowMapper;
    }

    @Override
    public void start(Promise<Void> startPromise) throws Exception {
        super.start(startPromise);
        System.out.println("CrudCmd");
        router = Router.router(vertx);
        //能处理body
        router.route().handler(BodyHandler.create());
        //加载crud默认路由
        initCrudRoute(router);
        System.out.println(router);
        // 统一异常处理
        initRouteError(router);

    }

    private void initRouteError(Router router) {
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

    private Router initCrudRoute(Router router) {

        router.post("/_query").handler(this::query);
        router.post("/_queryPage").handler(this::queryPage);
        router.get("/_detail/:id").handler(this::detail);
        router.delete("/_delete/:id").handler(this::delete);
        router.post("/_insert").handler(this::insert);
        router.post("/_update").handler(this::update);
        return router;
    }

    private void queryPage(RoutingContext routingContext) {
        //查询条件
        Map<String,Object> paramMap = new HashMap<>();
        paramMap.put("current",1);
        paramMap.put("pageSize",10);

        crudService.queryPager(paramMap)
                .onSuccess(pager -> {
                    Pager pager1 = (Pager) pager;
                    successfulled(routingContext,pager1);
                }).onFailure(event -> {
                    System.out.println(event);
                    error(routingContext, (Throwable) event);
                });
    }

    private void update(RoutingContext routingContext) {
    }

    private void insert(RoutingContext routingContext) {
        JsonObject jsonObject = routingContext.getBodyAsJson();
        Map map= new HashMap();

        jsonObject.stream().forEach(stringObjectEntry -> {
             map.put(stringObjectEntry.getKey(),stringObjectEntry.getValue());
        });
        map.put("id", UUID.randomUUID().hashCode());
        map.put("createTime", System.currentTimeMillis());
        map.put("updateTime",System.currentTimeMillis());
        crudService.insert(map).onSuccess(ex->successfulled(routingContext,"success"))
                .onFailure(ex->error(routingContext,(Throwable) ex));

    }

    private void delete(RoutingContext routingContext) {
        String id = routingContext.pathParam("id");
        crudService.delete(Collections.singletonMap("id",id)).onSuccess(ex->successfulled(routingContext,"success"))
                .onFailure(ex->error(routingContext,(Throwable) ex));

    }

    private void detail(RoutingContext routingContext) {
        String id = routingContext.pathParam("id");
        crudService.detail(id,rowMapper)
                .onSuccess(list -> {
                    RowSet rowSet = (RowSet) list;
                            rowSet.iterator().forEachRemaining(row ->{
                                BaseModel baseModel = (BaseModel)row;
                                successfulled(routingContext, baseModel.toJson());
                    });
                    successfulled(routingContext, new JsonObject());
                }).onFailure(event -> {
                    System.out.println(event);
                    error(routingContext, (Throwable) event);
                });
    }

    private void query(RoutingContext routingContext) {
        //查询条件
        Map<String,Object> paramMap = new HashMap<>();
        crudService.query(paramMap,rowMapper)
                .onSuccess(list -> {
                    RowSet rowSet = (RowSet) list;
                    List date = new ArrayList();
                    rowSet.iterator().forEachRemaining(row ->{
                       BaseModel baseModel =  (BaseModel)row;
                            date.add(baseModel.toJson());
                        });
                    successfulled(routingContext, date);
                }).onFailure(event -> {
                    System.out.println(event);
                    error(routingContext, (Throwable) event);
                });
    }

    public void createHttpServer(Router router){
        vertx.createHttpServer()
                .requestHandler(router)
//                .requestHandler(new VertxRequestHandler(vertx, deployment))
                .listen(8080, ar -> {
                    System.out.println("Server started on port "+ ar.result().actualPort());
                });
    }
}
