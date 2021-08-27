package org.isdp.vertx.tenant.cmd;

import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.sqlclient.templates.RowMapper;
import org.isdp.vertx.common.cmd.CrudCmd;
import org.isdp.vertx.common.service.CrudService;
import org.isdp.vertx.tenant.service.DicService;

/**
 */
public class DicCmd extends CrudCmd {

    DicService dicService = null;


    private DicCmd(Vertx vertx, Router router) {
        dicService =    DicService.create();
        // 加载特殊的api
        router.get(getRoutePath()+"/_queryWithTree/:itemId").handler(this::queryWithTree);
        initCmd( vertx,  router);
    }

    private void queryWithTree(RoutingContext routingContext) {
        String itemId = routingContext.pathParam("itemId");
        if(itemId == null)error(routingContext,new RuntimeException("itemId is not null!"));
        dicService.dictionaryDao.getDictionaryItemWithTree(itemId)
                .onSuccess(list->{
                    System.out.println(list);
                    successfulled(routingContext,list);
                })
                .onFailure(ex -> ex.printStackTrace())
        ;
    }

    @Override
    public CrudService getCrudService() {
        return dicService;
    }

    @Override
    public RowMapper getRowMapper() {
        return null;
    }

    @Override
    public String getRoutePath() {
        return "/isdp/saas/dic";
    }

    /**
     * 总入口
     * @param vertx
     * @param router
     * @return
     */

    public static DicCmd create(Vertx vertx, Router router){



        return new DicCmd(vertx,router);

    }

}
