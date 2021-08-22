package org.isdp.vertx.tenant.service;

import org.isdp.vertx.common.service.CrudService;
import org.isdp.vertx.datasource.DataSourceProvider;
import org.isdp.vertx.tenant.dao.TenantDao;

public class UsersService<Tenant> extends CrudService {
   static String querySql="select id,code,name,expired,type,create_time,update_time from tenant";

   static String insertSql = "insert into tenant (id, code, name, expired, type, create_time, update_time)\n" +
           "values (#{id},#{code},#{name},#{expired},#{type},#{create_time},#{update_time})";
    static String updateSql = "insert into tenant (id, code, name, expired, type, create_time, update_time)\n" +
            "values (#{id},#{code},#{name},#{expired},#{type},#{create_time},#{update_time})";

    static String deleteSql = "delete from tenant where id=#{id}";
    static String detailSql="select id,code,name,expired,type,create_time,update_time from tenant where id =#{id}";


    public static UsersService create(){
        return  new UsersService();
    };

    public UsersService() {

        setPool(DataSourceProvider.sqlClient);
        setCrudDao(TenantDao.create(sqlPool));

        setQueryPagerSql(querySql);
        setDelteSql(deleteSql);
        setInsertSql(insertSql);
        setQuerySql(querySql);
        setDetailSql(detailSql);
        setUpdateSql(updateSql);
    }
}
