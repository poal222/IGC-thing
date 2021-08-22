package org.isdp.vertx.tenant.model;


import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
import io.vertx.sqlclient.templates.annotations.Column;
import io.vertx.sqlclient.templates.annotations.RowMapped;
import org.isdp.vertx.common.model.BaseModel;

@DataObject(generateConverter = true)
@RowMapped
public class Tenant extends BaseModel<String> {

    @Column(name = "code")
    public String  code;
    @Column(name = "name")
    public String  name;
    @Column(name = "expired")
    public String  expired;
    @Column(name = "type")
    public String  type;


    public JsonObject toJson() {
        JsonObject json = new JsonObject();
        TenantConverter.toJson(this, json);
        return json;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getExpired() {
        return expired;
    }

    public void setExpired(String expired) {
        this.expired = expired;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
