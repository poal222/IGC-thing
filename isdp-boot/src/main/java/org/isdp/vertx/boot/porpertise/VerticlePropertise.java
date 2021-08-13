/*
 * Copyright (c) 2021. 四川高诚物联网科技有限公司.  All rights reserved.
 * 本软件基于Apache License 2.0
 * 任何单位及个人可以用于修改，分发及商业目的
 */

package org.isdp.vertx.boot.porpertise;

import io.vertx.core.json.JsonObject;

import java.io.Serializable;

public class VerticlePropertise implements Serializable {

  private static final String CONF_KEY = "configuration";
  private static final String INSTANCES_KEY = "instances";
  private static final String EXTRA_CLASSPATH_KEY = "extra-classpath";
  private static final String HA_KEY = "high-availability";
  private static final String ISOLATED_CLASSES_KEY = "isolated-classes";
  private static final String ISOLATION_GROUP_KEY = "isolation-group";
  private static final String MAXWORKER_EXECTIME_KEY = "max-worker-execution-time";
  private static final String WORKER_KEY = "worker";
  private static final String WORKER_POOLNAME_KEY = "worker-pool-name";
  private static final String WORKER_POOLSIZE_KEY = "worker-pool-size";

  private int getWorkerPoolSize(JsonObject config) {
    if (config.getInteger(WORKER_POOLSIZE_KEY) != null) {
      return config.getInteger(WORKER_POOLSIZE_KEY);
    }
    return 1;
  }

  private String getWorkerPoolName(JsonObject config) {
    if (config.getString(WORKER_POOLNAME_KEY)!= null) {
      return config.getString(WORKER_POOLNAME_KEY);
    }
    return null;
  }

  private boolean getWorker(JsonObject config) {
    if (config.getBoolean(WORKER_KEY)!= null) {
      return config.getBoolean(WORKER_KEY);
    }
    return false;
  }

  private long getMaxWorkerExecuteTime(JsonObject config) {
    if (config.getLong(MAXWORKER_EXECTIME_KEY)!= null) {
      return config.getLong(MAXWORKER_EXECTIME_KEY);
    }
    return Long.MAX_VALUE;
  }

  private String getIsolationGroup(JsonObject config) {
    if (config.getString(ISOLATION_GROUP_KEY)!= null) {
      return config.getString(ISOLATION_GROUP_KEY);
    }
    return null;
  }

//  private List<String> getIsolatedClasses(JsonObject config) {
//    if (config.get(ISOLATED_CLASSES_KEY)) {
//      return config.getStringList(ISOLATED_CLASSES_KEY);
//    }
//    return null;
//  }

  private boolean getHa(JsonObject config) {
    if (config.getBoolean(HA_KEY)!=null) {
      return config.getBoolean(HA_KEY);
    }
    return false;
  }

//  private List<String> getExtraClasspath(JsonObject config) {
//    if (config.hasPath(EXTRA_CLASSPATH_KEY)) {
//      return config.getStringList(EXTRA_CLASSPATH_KEY);
//    }
//    return null;
//  }
//
//  private JsonObject getConfig(Config config) {
//    if (config.hasPath(CONF_KEY)) {
//      return new JsonObject(config.getValue(CONF_KEY).render(ConfigRenderOptions.concise()));
//    }
//    return new JsonObject();
//  }
//
//  private int getInstances(JsonObject config) {
//    if (config.hasPath(INSTANCES_KEY)) {
//      return config.getInt(INSTANCES_KEY);
//    }
//    return 1;
//  }
}
