/*
 * Copyright (c) 2021. 四川高诚物联网科技有限公司.  All rights reserved.
 * 本软件基于Apache License 2.0
 * 任何单位及个人可以用于修改，分发及商业目的
 */

package org.isdp.vertx.boot.boot;


import io.vertx.core.json.JsonObject;

/**
 *  应用全局信息加载
 */
public class ApplicationContext {
  /**
   * 全局配置信息
   * 需要和vertcile的私有配置信息区分开
   */
  public static JsonObject APPLICAION_CONFIG = null;


}
