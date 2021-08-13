package org.isdp.vertx.cluster;

import io.vertx.core.*;
import io.vertx.core.shareddata.AsyncMap;
import io.vertx.core.spi.cluster.ClusterManager;
import io.vertx.core.streams.ReadStream;
import io.vertx.ext.cluster.infinispan.ClusterHealthCheck;
import io.vertx.ext.cluster.infinispan.InfinispanAsyncMap;
import io.vertx.ext.cluster.infinispan.InfinispanClusterManager;
import io.vertx.ext.healthchecks.HealthCheckHandler;
import io.vertx.ext.healthchecks.HealthChecks;
import io.vertx.ext.healthchecks.Status;
import io.vertx.ext.web.Router;
import org.infinispan.manager.DefaultCacheManager;
import org.isdp.vertx.boot.annotation.DeployVerticle;

/**
 * 集群管理检查器
 */
@DeployVerticle(name = "ClusterHealth")
public class ClusterHealthCmd extends AbstractVerticle {

  @Override
  public void start(Promise<Void> startPromise) throws Exception {
    super.start(startPromise);
//    Router route = Router.router(vertx);
//    1、创建健康监察器
    HealthChecks checks = healthCheck(vertx);
//    2、添加路由
    Router router = healthCheckHandler(vertx,checks);
    // 3、添加服务
    vertx.createHttpServer().requestHandler(router)
      .listen(9989);
  }

  //创建集群 健康监察
  public HealthChecks healthCheck(Vertx vertx) {
    Handler<Promise<Status>> procedure = ClusterHealthCheck.createProcedure(vertx, true);
    return HealthChecks.create(vertx).register("cluster-health", procedure);
  }
  //创建发送信息
  public Router healthCheckHandler(Vertx vertx, HealthChecks checks) {
    Router router = Router.router(vertx);
    router.get("/readiness").handler(HealthCheckHandler.createWithHealthChecks(checks));
    //在事件总线上开放健康检查功能
    vertx.eventBus().consumer("health",
      message -> checks.checkStatus()
        .onSuccess(message::reply)
        .onFailure(err -> message.fail(0, err.getMessage())));

    return router;
  }
}
