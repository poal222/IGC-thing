/*
 * Copyright (c) 2021. 四川高诚物联网科技有限公司.  All rights reserved.
 * 本软件基于Apache License 2.0
 * 任何单位及个人可以用于修改，分发及商业目的
 */

package org.isdp.vertx.boot.cache;

import io.vertx.core.json.JsonObject;

/**
 * 缓存配置属性
 * 全配置样例如下：
 *       "cache": {
 *         "ttl": 200,
 *         "name": "org.idsp.vertx.cache",
 *         "type": "redis",
 *         "enable": false
 *       }
 */
public class CacheOptions {
    /**
     * 默认不启用缓存
     */
    private boolean enable=false;
    /**
     * 缓存存续期
     */
    private int ttl=200;
    /**
     * 缓存配置文件路径，默认为isdp提供的实现
     * 支持自定义缓存实现方式
     */
    private String name="org.idsp.vertx.cache.CacheProvider";
    /**
     * 缓存类型，默认为本地缓存
     */
    private String type="local";

    public CacheOptions(String name, int ttl, String type,boolean enable) {
        this.ttl=ttl;
        this.name=name;
        this.type=type;
        this.enable = enable;
    }

    /**
     * 默认值
     */
    public CacheOptions() {

    }


    /**
     * 缓存配置加载
     * @param cacheConfig
     * @return
     */
    static CacheOptions create(JsonObject cacheConfig){
        String name = cacheConfig.getString("name","org.idsp.vertx.cache.CacheProvider");
        int ttl = cacheConfig.getInteger("ttl",200);
        String type = cacheConfig.getString("type","redis");
        boolean enable = cacheConfig.getBoolean("enable",false);
      return  new CacheOptions(name,ttl,type,enable);

    }

    public int getTtl() {
        return ttl;
    }

    public void setTtl(int ttl) {
        this.ttl = ttl;
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
}
