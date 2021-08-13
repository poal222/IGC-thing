/*
 * Copyright (c) 2021. 四川高诚物联网科技有限公司.  All rights reserved.
 * 本软件基于Apache License 2.0
 * 任何单位及个人可以用于修改，分发及商业目的
 */

package org.isdp.vertx.boot.annotation;

import io.vertx.core.DeploymentOptions;

import java.lang.annotation.*;

/**
 * 部署verticle
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface DeployVerticle {
  /**
   * 配置文件路径 可通过外部配置文件加载配置属性，如果没有，则调用默认配置
   * @return
   */
  String path() default "application.json";

  /**verticle name，建议必须唯一
   *
   * @return
   */
  String name() ;
}
