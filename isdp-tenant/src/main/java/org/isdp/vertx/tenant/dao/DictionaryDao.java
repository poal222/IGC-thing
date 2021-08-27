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


    /**
     * 查询字典名称之下的字典项
     * @param dicId
     * @return
     */
    public Future<List<DictionaryItem>> getTopDictionaryItem(String dicId){
        Promise promise = Promise.promise();
        List<JsonObject> dictionaryItemList = new ArrayList<>();
        SqlTemplate.forQuery(sqlClient,"SELECT * FROM pub_dic_item where dic_id =#{dicId}" +
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
        SqlTemplate.forQuery(sqlClient,"SELECT * FROM pub_dic_item where parent_id =#{itemId}")
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
    public Future<List<DictionaryItem>> getDictionaryItemWithTree(String itemId){
        Promise promise = Promise.promise();
        List<JsonObject> dictionaryItemList = new ArrayList<>();
        // 查询本级
        SqlTemplate.forQuery(sqlClient,"SELECT id,dic_id,parent_id,path,code,name,seq,tenant_id,create_time,update_time FROM pub_dic_item where id =#{itemId}" +
                        " and parent_id='#' " )
                .mapTo(DictionaryItemRowMapper.INSTANCE)
                .execute(Collections.singletonMap("itemId",itemId))
                .onSuccess(rowset -> {
                    // 递归 查询下一级及一下
                    rowset.forEach(dictionaryItem -> {

                        getChildWithTree(dictionaryItemList,dictionaryItem.getId());
                        dictionaryItemList.add(dictionaryItem.toJson());
                    });
                    promise.complete(dictionaryItemList);

                }).onFailure(ex ->ex.printStackTrace());
        return promise.future();
    }

    private void getChildWithTree(List<JsonObject> dictionaryItemList, String id) {
        // 查询本级
        SqlTemplate.forQuery(sqlClient,"SELECT * FROM pub_dic_item where id =#{itemId}")
                .mapTo(DictionaryItemRowMapper.INSTANCE)
                .execute(Collections.singletonMap("itemId",id))
                .onSuccess(rowset -> {
                    // 递归 查询下一级及一下
                    rowset.forEach(dictionaryItem ->{
                        dictionaryItemList.add(dictionaryItem.toJson());
                     getChildWithTree(dictionaryItemList, dictionaryItem.getId());
                    });
                }).onFailure(ex ->ex.printStackTrace());
    }
}
