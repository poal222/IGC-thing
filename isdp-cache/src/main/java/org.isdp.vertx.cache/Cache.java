/*
 * Copyright (c) 2021. 四川高诚物联网科技有限公司.  All rights reserved.
 * 本软件基于Apache License 2.0
 * 任何单位及个人可以用于修改，分发及商业目的
 */

package org.isdp.vertx.cache;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 缓存接口类，屏蔽缓存差异行
 */
public abstract class Cache<K,V> {
    final Map<K, V> m = new HashMap<>();

    final ReadWriteLock rwl = new ReentrantReadWriteLock();
    final Lock r = rwl.readLock();
    final Lock w = rwl.writeLock();

    /**
     * get key
     *
     * @param key
     * @return
     */
    public V get(K key) {
        r.lock();
        try {
            return m.get(key);
        } finally {
            r.unlock();
        }
    }

    /**
     * 获取当前缓存下面所有的key
     *
     * @return
     */
    public Set<K> keys() {
        return m.keySet();
    }


    /**
     * 返回当前缓存下面的entrySet
     *
     * @return
     */
    public Set<Map.Entry<K, V>> getEntrySet() {
        r.lock();
        try {
            return m.entrySet();
        } finally {
            r.unlock();
        }
    }

    /**
     * put key
     *
     * @param key
     * @param value
     * @return
     */
    public V put(K key, V value) {
        w.lock();
        try {
            return m.put(key, value);
        } finally {
            w.unlock();
        }
    }

    /**
     * remove key
     *
     * @param key
     * @return
     */
    public V remove(K key) {
        w.lock();
        try {
            return m.remove(key);
        } finally {
            w.unlock();
        }
    }

    /**
     * 模板方法，调用子类实现的init方法
     */
    void _init() {
        w.lock();
        try {
            init();
        } finally {
            w.unlock();
        }
    }

    /**
     * 缓存初始化，子类实现
     */
    protected abstract void init();
}
