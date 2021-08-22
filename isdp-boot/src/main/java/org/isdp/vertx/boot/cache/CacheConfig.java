/*
 * Copyright (c) 2021. 四川高诚物联网科技有限公司.  All rights reserved.
 * 本软件基于Apache License 2.0
 * 任何单位及个人可以用于修改，分发及商业目的
 */

package org.isdp.vertx.boot.cache;

import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CacheConfig {

    private static CacheOptions cacheOptions  = null;

    private static Logger logger  = LoggerFactory.getLogger(CacheConfig.class);


    public static void initEnableCache(JsonObject cacheConfig){

        
        if(null != cacheConfig){
            cacheOptions=CacheOptions.create(cacheConfig);
        }else{
            cacheOptions = new CacheOptions();
        }
        //启用缓存，初始化缓存配置组件
        if(cacheOptions.isEnable()){
            /* Step 1. Get ClassLoader */
            ClassLoader cl = Thread.currentThread().getContextClassLoader();  // 如何获得ClassLoader参考1

            /* Step 2. Load the class */
            Class cls = null; // 使用第一步得到的ClassLoader来载入B
            try {
                cls = cl.loadClass(cacheOptions.getName());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                logger.error("ClassNotFoundException:Isdp-cache is not supply!,please check your maven!");
            }
            try {
              Method method =  cls.getDeclaredMethod("init",String.class);
                method.invoke(cls.getDeclaredConstructor().newInstance(),cacheOptions.getType());
            } catch (NoSuchMethodException e) {
              logger.error("Isdp-cache is not supply!,please check your maven!");
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InstantiationException e) {
                e.printStackTrace();
            }
        }
        if(!cacheOptions.isEnable()){
            if(logger.isInfoEnabled()){
                logger.info("cache is not supply!");
            }
        }


    }

}
