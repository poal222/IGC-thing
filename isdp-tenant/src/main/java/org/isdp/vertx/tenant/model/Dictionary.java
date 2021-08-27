package org.isdp.vertx.tenant.model;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.Json;
import io.vertx.core.json.JsonObject;
import io.vertx.sqlclient.templates.annotations.Column;
import io.vertx.sqlclient.templates.annotations.RowMapped;
import org.isdp.vertx.common.model.BaseModel;

/**
 * 数据字典 实体类
 *
 */
@DataObject(generateConverter = true)
@RowMapped
public class Dictionary extends BaseModel<String> {

    /**
     * 编码
     */
    @Column(name = "code")
    private String code;

    /**
     * 名称
     */
    @Column(name = "name")
    private String name;
    /**
     * 排序号
     */
    @Column(name = "seq")
    private Integer seq;

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

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    @Override
    public JsonObject toJson() {
        JsonObject jsonObject = new JsonObject();
        DictionaryConverter.toJson(this,jsonObject);
        return jsonObject;
    }
}
