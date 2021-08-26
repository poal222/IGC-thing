package org.isdp.vertx.tenant.service;

import org.isdp.vertx.common.service.CrudService;
import org.isdp.vertx.datasource.DataSourceProvider;
import org.isdp.vertx.tenant.dao.DictionaryDao;
import org.isdp.vertx.tenant.dao.TenantDao;

/**
 *  数字字典服务层
 * @param <Dictionary>
 */
public class DicService <Dictionary> extends CrudService {

   public DictionaryDao dictionaryDao = null;
    static String querySql="select id,code,name,expired,type,create_time,update_time from tenant";

    static String insertSql = "insert into tenant (id, code, name, expired, type, create_time, update_time)\n" +
            "values (#{id},#{code},#{name},#{expired},#{type},#{create_time},#{update_time})";
    static String updateSql = "insert into tenant (id, code, name, expired, type, create_time, update_time)\n" +
            "values (#{id},#{code},#{name},#{expired},#{type},#{create_time},#{update_time})";

    static String deleteSql = "delete from tenant where id=#{id}";
    static String detailSql="select id,code,name,expired,type,create_time,update_time from tenant where id =#{id}";


    public static DicService create(){
        return  new DicService();
    };

    public DicService() {
        dictionaryDao=DictionaryDao.create(sqlPool);
        setPool(DataSourceProvider.sqlClient);
        setCrudDao(dictionaryDao);

        setQueryPagerSql(querySql);
        setDelteSql(deleteSql);
        setInsertSql(insertSql);
        setQuerySql(querySql);
        setDetailSql(detailSql);
        setUpdateSql(updateSql);
    }
}

