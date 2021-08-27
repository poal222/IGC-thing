package org.isdp.vertx.tenant.service;

import org.isdp.vertx.common.service.CrudService;
import org.isdp.vertx.datasource.DataSourceProvider;
import org.isdp.vertx.tenant.dao.DictionaryDao;

/**
 *  数字字典服务层
 * @param <Dictionary>
 */
public class DicService <Dictionary> extends CrudService {

   public DictionaryDao dictionaryDao = null;
    static String querySql="select id,code,name,seq,tenant_id,create_time,update_time from pub_dic ";

    static String insertSql = "INSERT INTO pub_dic(id,code,name,seq,tenant_id,create_time,update_time)VALUES(#{id},#{code},#{name},#{seq},#{tenant_id},#{createTime},#{updateTime})";
    static String updateSql = "UPDATE pub_dic SET id = #{id},code = #{code},name = #{name},seq = #{seq},tenant_id = #{tenant_id},create_time = #{createTime},update_time = #{updateTime}WHERE id = #{id}";

    static String deleteSql = "delete from pub_dic where id=#{id}";
    static String detailSql="select id,code,name,seq,tenant_id,create_time,update_time from pub_dic where id =#{id} ";


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

