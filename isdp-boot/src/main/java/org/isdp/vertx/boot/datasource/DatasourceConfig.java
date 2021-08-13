/*
 * Copyright (c) 2021. 四川高诚物联网科技有限公司.  All rights reserved.
 * 本软件基于Apache License 2.0
 * 任何单位及个人可以用于修改，分发及商业目的
 */

package org.isdp.vertx.boot.datasource;

import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class DatasourceConfig {

    private static DatasourceOptions datasourceOptions  = null;

    private static Logger logger  = LoggerFactory.getLogger(DatasourceConfig.class);


    public static void initEnableDataSource(Vertx vertx,JsonObject cacheConfig){
        if(null != cacheConfig){
          datasourceOptions= DatasourceOptions.create(cacheConfig);
        }else{
          datasourceOptions = new DatasourceOptions();
        }
        //启用缓存，初始化缓存配置组件
        if(datasourceOptions.isEnable()){
            /* Step 1. Get ClassLoader */
            ClassLoader cl = Thread.currentThread().getContextClassLoader();  // 如何获得ClassLoader参考1

            /* Step 2. Load the class */
            Class cls = null; // 使用第一步得到的ClassLoader来载入B
            try {
                cls = cl.loadClass(datasourceOptions.getName());
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
                logger.error("ClassNotFoundException:Isdp-DataSource is not supply!,please check your maven!");
            }
            try {
//              public  void init(Vertx vertx, String type, JsonObject config){
              Method method =  cls.getDeclaredMethod("init",new Class[]{Vertx.class,String.class,JsonObject.class});
                method.invoke(cls.getDeclaredConstructor().newInstance(),vertx,datasourceOptions.getType(),datasourceOptions.getConfig());
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
        if(!datasourceOptions.isEnable()){
            if(logger.isInfoEnabled()){
                logger.info("DataSource is not supply!");
            }
        }


    }

}
