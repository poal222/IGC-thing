/*
 * Copyright (c) 2021. 四川高诚物联网科技有限公司.  All rights reserved.
 * 本软件基于Apache License 2.0
 * 任何单位及个人可以用于修改，分发及商业目的
 */

package org.isdp.vertx.boot.boot;

import io.vertx.core.DeploymentOptions;
import io.vertx.core.Verticle;

public class VerticleContainer {
  /**
   * 需要被加载的 verticle 路径
   */
  private String verticle;
  /**
   * 需要被加载的 verticle对应的部署参数
   */
  private DeploymentOptions deploymentOptions = new DeploymentOptions();


  public String getVerticle() {
    return verticle;
  }

  public void setVerticle(String verticle) {
    this.verticle = verticle;
  }

  public DeploymentOptions getDeploymentOptions() {
    return deploymentOptions;
  }

  public void setDeploymentOptions(DeploymentOptions deploymentOptions) {
    this.deploymentOptions = deploymentOptions;
  }
}
