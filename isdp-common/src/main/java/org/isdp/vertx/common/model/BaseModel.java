package org.isdp.vertx.common.model;

import io.vertx.core.json.JsonObject;
import io.vertx.sqlclient.templates.annotations.Column;

import java.util.Date;

public abstract class  BaseModel<Pk> {


    /**
     * 租户ID
     */
    @Column(name = "tenant_id")
    private String tenantId;

    @Column(name = "id")
    public Pk Id;

    @Column(name = "create_time")
    public Date createTime;

    @Column(name = "update_time")
    public Date updateTime;

    public Pk getId() {
        return Id;
    }

    public void setId(Pk id) {
        Id = id;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public abstract JsonObject toJson();
}
