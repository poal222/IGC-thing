
/*
 * Copyright (c) 2021-2021. 四川高诚物联网科技有限公司.  All rights reserved.
 * 本软件基于Apache License 2.0
 * 任何单位及个人可以用于修改，分发及商业目的
 */

package org.isdp.vertx.boot.boot;

/**
 * Isdp应用启动配置文件
 */
public class ApplicationOptions {

  /**
   * 默认是 单机模式
   */
  private AppMode appMode=AppMode.standalone;
  /**
   * 默认是 开发模式
   */
  private String env = "dev";

  public AppMode getAppMode() {
    return appMode;
  }

  public void setAppMode(AppMode appMode) {
    this.appMode = appMode;
  }

  public String getEnv() {
    return env;
  }

  public void setEnv(String env) {
    this.env = env;
  }
}
