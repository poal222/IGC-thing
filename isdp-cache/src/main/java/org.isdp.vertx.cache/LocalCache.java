/*
 * Copyright (c) 2021. 四川高诚物联网科技有限公司.  All rights reserved.
 * 本软件基于Apache License 2.0
 * 任何单位及个人可以用于修改，分发及商业目的
 */

package org.isdp.vertx.cache;

public class LocalCache extends Cache{
    @Override
    protected void init() {
        register();
    }
    // 直接调用这个方法就注册了
    public void register() {
        CacheManager.getInstance().registerCache("TEST_CACHE_KEY", this);
    }
}
