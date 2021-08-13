/*
 * Copyright (c) 2021. 四川高诚物联网科技有限公司.  All rights reserved.
 * 本软件基于Apache License 2.0
 * 任何单位及个人可以用于修改，分发及商业目的
 */

package org.isdp.vertx.cache;


import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class CacheManager<K,V> {

    private final Map<String,Cache<K,V>> cacheMap = new ConcurrentHashMap<>();

    private CacheManager(){}

    public static volatile CacheManager cacheManager;

    /**
     * 单例模式 双重加锁
     * @return
     */
    public static CacheManager getInstance() {
        if (null == cacheManager) {
            synchronized (CacheManager.class) {
                if (null == cacheManager) {
                    cacheManager = new CacheManager();
                }
            }
        }
        return cacheManager;
    }

    /**
     * 判断该key的缓存是否存在
     * @param cacheKey
     * @return
     */
    public Boolean containsCacheKey(String cacheKey){
        return cacheMap.containsKey(cacheKey);
    }

    /**
     * 注册缓存
     * @param cacheKey 缓存key
     * @param cache
     */
    public void registerCache(String cacheKey, Cache<K, V> cache) {
        cache._init();
        cacheMap.put(cacheKey, cache);
    }

    /**
     * 从指定缓存中获取数据
     * @param cacheKey 缓存Key
     * @param key
     * @return
     */
    public V getValue(String cacheKey, K key) {
        Cache<K, V> cache = cacheMap.get(cacheKey);
        if(null == cache) {
            return cache.get(key);
        }
        return null;
    }

    /**
     * 获取指定的缓存
     * @param cacheKey
     * @return
     */
    public Cache<K,V> getCache(String cacheKey){
        return cacheMap.get(cacheKey);
    }


    /**
     * 设置缓存
     * @param cacheKey 缓存Key
     * @param key
     * @param value
     */
    public void put(String cacheKey, K key, V value) {
        Cache<K, V> cache = cacheMap.get(cacheKey);
        if(null == cache) {
            cache.put(key, value);
        }
    }

    /**
     * 设置缓存 并返回修改值
     * @param cacheKey 缓存Key
     * @param key
     * @param value
     */
    public V putAndSet(String cacheKey, K key, V value) {
        Cache<K, V> cache = cacheMap.get(cacheKey);
        if(null == cache) {
            cache.put(key, value);
        }
        return value;
    }

    /**
     * 从指定缓存中删除数据
     * @param cacheKey 缓存Key
     * @param key
     */
    public V remove(String cacheKey, K key) {
        Cache<K, V> cache = cacheMap.get(cacheKey);
        if(null == cache) {
            return cache.remove(key);
        }
        return null;
    }
}

