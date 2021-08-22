/*
 * Copyright (c) 2021. 四川高诚物联网科技有限公司.  All rights reserved.
 * 本软件基于Apache License 2.0
 * 任何单位及个人可以用于修改，分发及商业目的
 */

package org.isdp.vertx.boot.datasource;

import io.vertx.core.json.JsonObject;

/**
 * 缓存配置属性

 */
public class DatasourceOptions {
    /**
     * 默认不启用数据源服务
     */
    private boolean enable=false;
  /**
   * 默认 Mysql
   */
  private String  type="Mysql";
  /**
   * 默认不启用缓存
   */
  private JsonObject config=null;

    /**
     * 缓存配置文件路径，默认为isdp提供的实现
     * 支持自定义缓存实现方式
     */
    private String name="org.isdp.vertx.datasource.DataSourceProvider";


    public DatasourceOptions(String name, String type,JsonObject config,Boolean enable ) {
        this.name=name;
        this.enable = enable;
        this.type = type;
        this.config = config;
    }

    /**
     * 默认值
     */
    public DatasourceOptions() {

    }


    /**
     * 缓存配置加载
     * @param cacheConfig
     * @return
     */
    static DatasourceOptions create(JsonObject cacheConfig){
        String name = cacheConfig.getString("name","org.isdp.vertx.datasource.DataSourceProvider");
        String type = cacheConfig.getString("type","Mysql");
        boolean enable = cacheConfig.getBoolean("enable",false);
      return  new DatasourceOptions(name,type,cacheConfig,enable);

    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isEnable() {
        return enable;
    }

    public void setEnable(boolean enable) {
        this.enable = enable;
    }

  public JsonObject getConfig() {
    return config;
  }

  public void setConfig(JsonObject config) {
    this.config = config;
  }
}
