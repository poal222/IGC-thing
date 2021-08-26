package org.isdp.vertx.tenant.model;

import io.vertx.codegen.annotations.DataObject;
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


    @Override
    public JsonObject toJson() {
        return null;
    }
}
