package org.isdp.vertx.tenant.model;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;
import io.vertx.sqlclient.templates.annotations.Column;
import io.vertx.sqlclient.templates.annotations.RowMapped;
import org.isdp.vertx.common.model.BaseModel;

/**
 * 数据字典内容 实体类
 *
 */
@DataObject(generateConverter = true)
@RowMapped
public class DictionaryItem extends BaseModel<String> {

    /**
     * 所属字典
     */
    @Column(name = "dic_id")
    private String DicID;
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
     * 字典上级
     */
    @Column(name = "parent_id")
    private String parentID;

    /**
     * 数路径
     */
    @Column(name = "path")
    private String path;
    /**
     * 排序号
     */
    @Column(name = "seq")
    private Integer seq;

    public String getDicID() {
        return DicID;
    }

    public void setDicID(String dicID) {
        DicID = dicID;
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

    public String getParentID() {
        return parentID;
    }

    public void setParentID(String parentID) {
        this.parentID = parentID;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
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
        DictionaryItemConverter.toJson(this,jsonObject);
        return jsonObject;
    }
}
