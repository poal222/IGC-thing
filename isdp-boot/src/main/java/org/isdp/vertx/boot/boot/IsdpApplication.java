/*
 * Copyright (c) 2021. 四川高诚物联网科技有限公司.  All rights reserved.
 * 本软件基于Apache License 2.0
 * 任何单位及个人可以用于修改，分发及商业目的
 */

package org.isdp.vertx.boot.boot;

import io.vertx.config.ConfigRetriever;
import io.vertx.config.ConfigRetrieverOptions;
import io.vertx.config.ConfigStoreOptions;
import io.vertx.core.*;
import io.vertx.core.json.JsonObject;

import org.isdp.vertx.boot.annotation.Application;
import org.isdp.vertx.boot.cache.CacheConfig;
import org.isdp.vertx.boot.datasource.DatasourceConfig;
import org.isdp.vertx.boot.porpertise.ApplicationPropertise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * 启动加载器，
 * 使用组装的模式
 */
public class IsdpApplication {

  static Logger logger = LoggerFactory.getLogger(IsdpApplication.class);
  /**
   * 命令行参数
   */
  protected static List<String> PROCESS_ARGS;
  public static List<String> getProcessArguments() {
    return PROCESS_ARGS;
  }
  private String DefalutApplicationConfig = "application.json";

  public void setDefalutApplicationConfig(String defalutApplicationConfig) {
    DefalutApplicationConfig = defalutApplicationConfig;
  }

  /**
   * 启动 参数
   */
  private ApplicationOptions applicationOptions=null;
  /**
   * 系统参数
   */
  private static ApplicationPropertise applicationPropertise = null;
  /**
   * 单态模式 启动app
   */
  private  IsdpApplication isdpApplication = null;
  /**
   * 配置文件集合
   */
  private  JsonObject isdpCurrentConfig = new JsonObject();


  private IsdpApplication(ApplicationOptions applicationOptions) {
    this.applicationOptions = applicationOptions;

  }

  public IsdpApplication() {
    // 默认参数
    applicationOptions = new ApplicationOptions();
  }

  public ApplicationOptions getApplicationOptions() {
    return applicationOptions;
  }

  public void setApplicationOptions(ApplicationOptions applicationOptions) {
    this.applicationOptions = applicationOptions;
  }
  //1、运行前配置

  /**
   * 启动前配置，用来加载自定义系统配置
   * @param applicationOptions
   * @return
   */
  public IsdpApplication beforeStart(ApplicationOptions applicationOptions){
    isdpApplication = new IsdpApplication(applicationOptions);
    return isdpApplication;
  }

  /**
   * 启动后配置，用来加载自定义其它设置
   * @param applicationOptions
   * @return
   */
  public IsdpApplication afterStart(ApplicationOptions applicationOptions){
    return isdpApplication;
  }

  /**
   * 实际run 但是需要注意一定要把异步方法包装在同步流程里，避免配置文件加载不到的问题
   * @param runBootClass
   * @param vertx
   */
  public void Run(Class<?> runBootClass, Vertx vertx){
    if(logger.isInfoEnabled()){
      logger.info("Isdp application current env {} ",applicationOptions.getEnv());
      logger.info("Isdp application appMode  {}",applicationOptions.getAppMode());
    }


    // 0 加载默认配置文件地址
    Future<JsonObject> jsonObjectFuture =  initConfig(vertx);

    jsonObjectFuture.onSuccess(config->{
      // tag::[0:全局加载配置文件]
      isdpCurrentConfig = config.getJsonObject("isdp",new JsonObject()).getJsonObject(applicationPropertise.getEnv(),new JsonObject());

      //    1、 加载配置组件
      ConfigurationLoader configurationLoader = new ConfigurationLoader();
//    2、加载启动模式verticle
      Future<Vertx>  vertxFuture =  modeHandler(vertx,getApplicationOptions().getAppMode(),applicationPropertise.getVertxOptions());
//    3、 加载 verticle
      VerticleLoader verticleLoader =  getVerticleLoader(runBootClass,vertx,isdpCurrentConfig);
      // 4、部署类
      this.deployVerticle( vertxFuture,verticleLoader);
      // 5、 加载 缓存组件
      this.initCache(config);
      // 5、初始化默认数据源
      this.initDataSource(vertxFuture,config);

      if(logger.isInfoEnabled()){
        logger.info("current env {} and config is {}",applicationPropertise.getEnv(),isdpCurrentConfig);
      }
    });



  }

  /**
   * 加载缓存组件
   * @param config
   */
  private void initCache(JsonObject config) {
    CacheConfig.initEnableCache(config.getJsonObject("isdp").getJsonObject(applicationOptions.getEnv()).getJsonObject("cache"));
  }

  /**
   * 初始化默认数据源
   * 如果存在datasource数据源的话
   * @param vertxFuture
   * @param config
   */
  private void initDataSource(Future<Vertx> vertxFuture, JsonObject config) {
    if(applicationPropertise.getDataSourceconfig()!=null){
      vertxFuture.onSuccess(vertx->{
        DatasourceConfig.initEnableDataSource(vertx,applicationPropertise.getDataSourceconfig());
      });

    }
  }

  /**
   * 部署vertx
   * @param vertxFuture
   * @param verticleLoader
   */
  private void deployVerticle(Future<Vertx> vertxFuture, VerticleLoader verticleLoader) {
    vertxFuture.onSuccess(vertx1 -> {
      verticleLoader.getVerticleList()
        .forEach(verticle -> {
          // 加载部署
          vertx1.deployVerticle(verticle.getVerticle(),verticle.getDeploymentOptions());
          if (logger.isDebugEnabled()){
            logger.debug("deploy Verticle {} is successfulled! ",verticle.getVerticle());
            logger.debug("deploy Verticle {} DeploymentOptions  is: {}! ",verticle.getVerticle(),verticle.getDeploymentOptions());
          }
        });
    });
    vertxFuture.onFailure(ex -> {
      ex.printStackTrace();
      logger.error("IsdpApplication run error {}",ex.getCause());
    });
  }
  /**
   *  加载deploy 类至verticle 类加载器中
   * @param testBootClass
   * @param vertx
   * @param config 部署配置
   * @return
   */
  private VerticleLoader getVerticleLoader(Class<? extends Object> testBootClass, Vertx vertx,JsonObject config){
    // 加载文件路径，使用注解方式获取
    Application application= testBootClass.getAnnotation(Application.class);
    if(application == null){
      throw new RuntimeException("application excepiton : current application is absent @Application");
    }
    if(application.scanPackages().length == 0){
      application.scanPackages()[0]=testBootClass.getPackage().getName();
    }
    // 加载verticle
    return VerticleLoader.getInstance(application.scanPackages(),vertx,config);
  }

  /**
   * 加载默认配置文件
   * @param vertx
   * @return
   */
  private  Future<JsonObject> initConfig(Vertx vertx) {
    if(logger.isInfoEnabled()){
      logger.info(" Loaded DefalutApplicationConfig is {}",DefalutApplicationConfig);
    }

    ConfigStoreOptions sysPropsStore = new ConfigStoreOptions()
      .setType("file")
      .setConfig(new JsonObject().put("path", DefalutApplicationConfig));
    ConfigRetrieverOptions options = new ConfigRetrieverOptions()
      .addStore(sysPropsStore);
    ConfigRetriever retriever = ConfigRetriever.create(vertx, options);
    // add sys propertise
    initSysConfig(retriever.getConfig(), applicationOptions.getEnv());

    return retriever.getConfig();

  }

  private  void initSysConfig(Future<JsonObject> config, String env) {
    config.onSuccess(event -> {

      applicationPropertise = ApplicationPropertise.creat(event,env);
      logger.info(" applicationPropertise is {}",applicationPropertise);
    });
    config.onFailure(event -> {
      logger.error("isdp SysConfig add error {} ",event.getCause());
      event.printStackTrace();
    });
  }

  ;

  /**
   * 加载不同模式的vertx 部署
   * @param vertx
   * @param appMode 模式选择器
   * @param vertxOptions 参数配置
   * @return
   */
  private static Future<Vertx> modeHandler(Vertx vertx, AppMode appMode, VertxOptions vertxOptions){
    Promise<Vertx> promise = Promise.promise();
    if(appMode==AppMode.standalone){
      promise.complete(vertx);
    }
    if(appMode==AppMode.cluster){
      return Vertx.clusteredVertx(vertxOptions);
    }
    return promise.future();
  }

  /**
   *
   * @param args 接受来自于命令行的参数
   * @param bootClass 启动类
   * @param vertx vertx
   */
    public void Run(String[] args, Class<?> bootClass, Vertx vertx) {
      // 加载 命令行参数
      this.dispach(args);
      // 执行启动
      Run(bootClass,vertx);

    }

  private void dispach(String[] args) {
    PROCESS_ARGS = Collections.unmodifiableList(Arrays.asList(args));
    System.out.println(PROCESS_ARGS);
    PROCESS_ARGS.stream().forEach(s -> {
      // 集群模式
      if(s.startsWith("-cluster")){
        getApplicationOptions().setAppMode(AppMode.cluster);
      }
      // 配置文件
      if(s.startsWith("-conf=")){
        setDefalutApplicationConfig(s.substring("-conf=".length()));
      }
      if(s.startsWith("-env=")){
        getApplicationOptions().setEnv(s.substring("-env=".length()));
      }
    });

  }
}
