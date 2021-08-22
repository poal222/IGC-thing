package org.isdp.vertx.datasource.ddl;

import io.vertx.codegen.annotations.DataObject;
import io.vertx.core.json.JsonObject;

import java.io.Serializable;
import java.util.List;

/**
 * 分页管理器
 * @author 王岗
 */
@DataObject(generateConverter = true)
public class Pager implements Serializable  {

  private List<Object> list;

  private Integer current;
  private Integer pageSize;
  private Integer total;

  public List<Object> getList() {
    return list;
  }

  public void setList(List<Object> list) {
    this.list = list;
  }

  public Integer getCurrent() {
    return current;
  }

  public void setCurrent(Integer current) {
    this.current = current;
  }

  public Integer getPageSize() {
    return pageSize;
  }

  public void setPageSize(Integer pageSize) {
    this.pageSize = pageSize;
  }

  public Integer getTotal() {
    return total;
  }

  public void setTotal(Integer total) {
    this.total = total;
  }

  @Override
  public String toString() {
    return "Pager{" +
      "list=" + list +
      ", current=" + current +
      ", pageSize=" + pageSize +
      ", total=" + total +
      '}';
  }

  public JsonObject toJson() {
    JsonObject json = new JsonObject();
    PagerConverter.toJson(this, json);
    return json;
  }
}
