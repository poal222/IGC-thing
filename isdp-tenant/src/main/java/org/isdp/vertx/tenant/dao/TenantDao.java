package org.isdp.vertx.tenant.dao;

import io.vertx.sqlclient.SqlClient;
import org.isdp.vertx.common.dao.CrudDao;
import org.isdp.vertx.tenant.model.Tenant;

public class TenantDao extends CrudDao<Tenant> {

    public static TenantDao create(SqlClient sqlClient){
        return new TenantDao(sqlClient);
    }

    public TenantDao(SqlClient sqlClient) {
        super(sqlClient);
    }

}
