package org.isdp.vertx.tenant.dao;

import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.json.JsonObject;
import io.vertx.sqlclient.SqlClient;
import io.vertx.sqlclient.templates.SqlTemplate;
import org.isdp.vertx.common.dao.CrudDao;
import org.isdp.vertx.tenant.model.DictionaryItem;
import org.isdp.vertx.tenant.model.DictionaryItemRowMapper;
import org.isdp.vertx.tenant.model.Tenant;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * 数据字典 实体类
 *
 */

public class DictionaryDao  extends CrudDao<Tenant> {

    public static DictionaryDao create(SqlClient sqlClient){
        return new DictionaryDao(sqlClient);
    }

    public DictionaryDao(SqlClient sqlClient) {
        super(sqlClient);
    }


    private static JsonObject treeJsonObject = new JsonObject();


    /**
     * 查询字典名称之下的字典项
     * @param dicId
     * @return
     */
    public Future<List<DictionaryItem>> getTopDictionaryItem(String dicId){
        Promise promise = Promise.promise();
        List<JsonObject> dictionaryItemList = new ArrayList<>();
        SqlTemplate.forQuery(sqlClient,"SELECT id,dic_id,parent_id,path,code,name,seq,tenant_id,create_time,update_time FROM pub_dic_item where dic_id =#{dicId}" +
                " and parentID='#' " )
                .mapTo(DictionaryItemRowMapper.INSTANCE)
                .execute(Collections.singletonMap("dicId",dicId))
                .onSuccess(rowset -> {
                    rowset.forEach(dictionaryItem -> {

                        dictionaryItemList.add(dictionaryItem.toJson());
                    });
                    promise.complete(dictionaryItemList);
                }).onFailure(ex ->ex.printStackTrace());
        return promise.future();
    }
    /**
     * 查询字典 获取下一级字典项
     * @param itemId 字典项id
     * @return
     */
    public Future<List<DictionaryItem>> getChild(String itemId){
        Promise promise = Promise.promise();
        List<JsonObject> dictionaryItemList = new ArrayList<>();
        SqlTemplate.forQuery(sqlClient,"SELECT id,dic_id,parent_id,path,code,name,seq,tenant_id,create_time,update_time FROM pub_dic_item where parent_id =#{itemId}")
                .mapTo(DictionaryItemRowMapper.INSTANCE)
                .execute(Collections.singletonMap("itemId",itemId))
                .onSuccess(rowset -> {
                    rowset.forEach(dictionaryItem -> {
                        dictionaryItemList.add(dictionaryItem.toJson());
                    });
                    promise.complete(dictionaryItemList);
                }).onFailure(ex ->ex.printStackTrace());
        return promise.future();
    }
    /**
     * 查询字典 生成树形结构，全同步
     * @param itemId 字典项id
     * @return
     */
    public Future<JsonObject> getDictionaryItemWithTree(String itemId){
        Promise promise = Promise.promise();
        // 查询本级
        SqlTemplate.forQuery(sqlClient,"SELECT id,dic_id,parent_id,path,code,name,seq,tenant_id,create_time,update_time FROM pub_dic_item where id =#{itemId}" )
                .mapTo(DictionaryItemRowMapper.INSTANCE)
                .execute(Collections.singletonMap("itemId",itemId))
                .onSuccess(rowset -> {
                   if(rowset.size()==0){
                       promise.complete();
                   }
                    rowset.forEach(dictionaryItem -> {
//                        getChilds(dictionaryItem,promise);

                        revices(dictionaryItem,promise);

                    });

                }).onFailure(ex ->ex.printStackTrace());
        return promise.future();
    }

    /**
     * 查询下一级
     * @param dictionaryItem
     * @return
     */
    private void revices(DictionaryItem dictionaryItem,Promise promise){
        selfAndChild(dictionaryItem,promise);
//        promise.complete( selfAndChild(dictionaryItem).result());
//        promise.tryComplete(promise1.future().result());
    }

    private synchronized Future<DictionaryItem> selfAndChild(DictionaryItem dictionaryItem, Promise promise1){
        Promise<DictionaryItem> promise = Promise.promise();
        // 查询下级
        SqlTemplate.forQuery(sqlClient,"SELECT id,dic_id,parent_id,path,code,name,seq,tenant_id,create_time,update_time FROM pub_dic_item where parent_id =#{itemId}")
                .mapTo(DictionaryItemRowMapper.INSTANCE)
                .execute(Collections.singletonMap("itemId",dictionaryItem.getId()))
                .onSuccess(rs ->{
                    if(rs.size()==0){
                        System.out.println("sdsd");
                        promise.tryComplete(dictionaryItem);
                    }else {
                     rs.forEach(dictionaryItem1 ->{
                               System.out.println("dictionaryItem1="+dictionaryItem1);
                               selfAndChild(dictionaryItem1, promise).onSuccess(d->{
                                   dictionaryItem.getChildren().add(dictionaryItem1.toJson());
                                   System.out.println("ssdsd"+dictionaryItem.toJson());
                                   promise.tryComplete(dictionaryItem);
                                   promise1.complete(dictionaryItem.toJson());
                               });
                       });
                    }
                });
        return promise.future();
    }

//    private void getChilds(DictionaryItem dictionaryItem,Promise promise) {
//
//        // 查询本级
//        SqlTemplate.forQuery(sqlClient,"SELECT id,dic_id,parent_id,path,code,name,seq,tenant_id,create_time,update_time FROM pub_dic_item where parent_id =#{itemId}")
//                .mapTo(DictionaryItemRowMapper.INSTANCE)
//                .execute(Collections.singletonMap("itemId",dictionaryItem.getId()))
//                .onSuccess(rowset -> {
//                    //如果 没有下级，则终止
//                    if(rowset.size() == 0 ){
//                        promise.tryComplete(dictionaryItem.toJson());
//                    }else{
//                        // 递归 查询下一级及一下
//                        rowset.forEach(dictionaryItem1 -> {
//                            dictionaryItem.getChildren().add(dictionaryItem1.toJson());
//                            getChilds(dictionaryItem1,promise);
//                        });
//                        promise.tryComplete(dictionaryItem.toJson());
//                    }
//                }).onFailure(ex ->ex.printStackTrace());
//    }
}


