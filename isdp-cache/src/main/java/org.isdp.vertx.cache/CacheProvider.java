/*
 * Copyright (c) 2021. 四川高诚物联网科技有限公司.  All rights reserved.
 * 本软件基于Apache License 2.0
 * 任何单位及个人可以用于修改，分发及商业目的
 */

package org.isdp.vertx.cache;

/**
 * 缓存接口类，屏蔽缓存差异行
 */
public  class CacheProvider {

    public static Cache cache= null;

   public  void init(String type){
        if("local".equalsIgnoreCase(type)){
            cache = new LocalCache();
        }
       if("Infinispan".equalsIgnoreCase(type)){
           cache = new InfinispanCache();
       }
       if("redis".equalsIgnoreCase(type)){
           cache = new LocalCache();
       }

    }
}
