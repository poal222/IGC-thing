/*
 * Copyright (c) 2021. 四川高诚物联网科技有限公司.  All rights reserved.
 * 本软件基于Apache License 2.0
 * 任何单位及个人可以用于修改，分发及商业目的
 */

package org.isdp.vertx.boot.porpertise;


import io.vertx.core.VertxOptions;
import io.vertx.core.json.JsonObject;


public class ApplicationPropertise {
    /**
     * 应用名称
     */
    private String name;
    /**
     * 环境参数
     */
    private String env;

    /**
     * VertxOptions
     */
    private VertxOptions vertxOptions;
    /**
     * dataSource
     */
    private JsonObject dataSourceconfig;

    public JsonObject getDataSourceconfig() {
        return dataSourceconfig;
    }

    public void setDataSourceconfig(JsonObject dataSourceconfig) {
        this.dataSourceconfig = dataSourceconfig;
    }

    public VertxOptions getVertxOptions() {
        return vertxOptions == null ? new VertxOptions() : vertxOptions;
    }

    public void setVertxOptions(VertxOptions vertxOptions) {
        this.vertxOptions = vertxOptions;
    }

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ApplicationPropertise(JsonObject sysConfig, String env) {
        setName(sysConfig.getString("app_name", "isdpApp"));
        setEnv(env);
//      //默认读取dev开发环境配置
        JsonObject envConfig = sysConfig.getJsonObject(env);
//      JsonObject serverConfig = envConfig.getJsonObject("server");
        JsonObject vertxConfig = envConfig.getJsonObject("vertx");

        JsonObject dataSourceConfig = envConfig.getJsonObject("dataSource");

        if(dataSourceConfig!=null){
            setDataSourceconfig(dataSourceConfig);
        }
//      JsonObject customConfig = envConfig.getJsonObject("custom");
      if(null != vertxConfig){
        setVertxOptions(new VertxOptions(vertxConfig));
      }else{
        setVertxOptions(new VertxOptions());
      }


    }


    public static ApplicationPropertise creat(JsonObject configObject, String env) {
        JsonObject sysConfig = configObject.getJsonObject("isdp");

        if (sysConfig == null) {
            sysConfig = new JsonObject();
        }
        return new ApplicationPropertise(sysConfig, env);


    }


  @Override
  public String toString() {
    return "ApplicationPropertise{" +
      "name='" + name + '\'' +
      ", env='" + env + '\'' +
      ", vertxOptions=" + vertxOptions +
      ", dataSourceconfig=" + dataSourceconfig +
      '}';
  }
}
