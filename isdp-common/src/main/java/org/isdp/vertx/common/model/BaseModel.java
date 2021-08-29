package org.isdp.vertx.common.model;

import io.vertx.core.json.JsonObject;
import io.vertx.sqlclient.templates.annotations.Column;

public abstract class  BaseModel<Pk> {


    /**
     * 租户ID
     */
    @Column(name = "tenant_id")
    public String tenantId;

    @Column(name = "id")
    public String id;

    @Column(name = "create_time")
    public String createTime;

    @Column(name = "update_time")
    public String updateTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(String updateTime) {
        this.updateTime = updateTime;
    }

    public String getTenantId() {
        return tenantId;
    }

    public void setTenantId(String tenantId) {
        this.tenantId = tenantId;
    }

    public abstract JsonObject toJson();
}
