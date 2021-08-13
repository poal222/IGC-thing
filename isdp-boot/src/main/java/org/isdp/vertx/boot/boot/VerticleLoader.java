/*
 * Copyright (c) 2021. 四川高诚物联网科技有限公司.  All rights reserved.
 * 本软件基于Apache License 2.0
 * 任何单位及个人可以用于修改，分发及商业目的
 */

package org.isdp.vertx.boot.boot;

import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Future;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;
import io.vertx.core.json.JsonObject;
import org.isdp.vertx.boot.annotation.DeployVerticle;

import java.util.*;

/**
 * verticle加载器
 */
public class VerticleLoader {

  /**
   * 参数定义
   */
  private static final String CONF_KEY = "configuration";
  private static final String INSTANCES_KEY = "instances";
  private static final String HA_KEY = "high-availability";
  private static final String MAXWORKER_EXECTIME_KEY = "max-worker-execution-time";
  private static final String WORKER_KEY = "worker";
  private static final String WORKER_POOLNAME_KEY = "worker-pool-name";
  private static final String WORKER_POOLSIZE_KEY = "worker-pool-size";

  private VerticleLoader(String[] deployPaths, Vertx vertx, JsonObject config) {
    Arrays.stream(deployPaths)
            .forEach(paths ->{
              List<String> classNames = PackageUtil.getClassName(paths, true);
              if (classNames != null) {
                for (String className : classNames) {
                  // 加载deploy
                  try {
                    Class c = Class.forName(className);
                    if(c.isAnnotationPresent(DeployVerticle.class)) {
                      // 加载文件路径，使用注解方式获取
                      DeployVerticle loadVerticle= (DeployVerticle) c.getAnnotation(DeployVerticle.class);
//
                      VerticleContainer  verticleContainer = new VerticleContainer();
                      verticleContainer.setVerticle(className);
                      // 加载 对应的配置文件，如果没有，使用默认配置
                      String name = loadVerticle.name();
                      // 加载统一的配置信息
                      verticleContainer.setDeploymentOptions( initDeploymentOptions(loadVerticle,vertx,config));
                      verticleList.add(verticleContainer);
                    }
                  } catch (ClassNotFoundException e) {
                    e.printStackTrace();
                  }
                }
              }
            });
  }

  private DeploymentOptions initDeploymentOptions(DeployVerticle loadVerticle, Vertx vertx, JsonObject config) {

      DeploymentOptions temp = new DeploymentOptions();
      //获取具体指标
      JsonObject jsonObject=config.getJsonObject(loadVerticle.name());
      if(jsonObject !=null ){
        temp.setInstances(jsonObject.getInteger(INSTANCES_KEY))
                .setConfig(jsonObject.getJsonObject(CONF_KEY))
                .setHa(jsonObject.getBoolean(HA_KEY))
                .setMaxWorkerExecuteTime(jsonObject.getInteger(MAXWORKER_EXECTIME_KEY))
                .setWorker(jsonObject.getBoolean(WORKER_KEY))
                .setWorkerPoolName(jsonObject.getString(WORKER_POOLNAME_KEY))
                .setWorkerPoolSize(jsonObject.getInteger(WORKER_POOLSIZE_KEY));
      }
    return temp;
  }

  /**
   * verticle 注册器
   */
  List<VerticleContainer> verticleList = new ArrayList<>();

  public static VerticleLoader getInstance(String[] deployPaths, Vertx vertx, JsonObject config) {
    return  new VerticleLoader(deployPaths,vertx,config);
  }

  public List<VerticleContainer> getVerticleList() {
    return verticleList;
  }

  public void setVerticleList(List<VerticleContainer> verticleList) {
    this.verticleList = verticleList;
  }
}
