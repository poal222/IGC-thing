package org.isdp.vertx.gateway.circuit;

import io.vertx.circuitbreaker.CircuitBreaker;
import io.vertx.circuitbreaker.CircuitBreakerOptions;
import io.vertx.core.Promise;
import io.vertx.core.Vertx;

/**
 * 使用断路器
 * 使用断路器需要按以下步骤进行：
 *
 * 创建一个断路器，并配置成你所需要的超时，最大故障次数等参数
 *
 * 使用断路器执行代码
 *
 * 重要！！！ 断路器应该是稳定的单例，而不是每次使用就重新创建它。推荐将该单例存放在某个领域中。
 */
public class CircuitBreakerHandler {

    public static CircuitBreaker getBreaker() {
        return breaker;
    }

    private static  CircuitBreaker breaker = null;

    private CircuitBreakerHandler(String circuitName,Vertx vertx) {
        this.breaker = CircuitBreaker.create(circuitName, vertx,
                new CircuitBreakerOptions()
                        .setMaxFailures(5) // 最大失败数
                        .setTimeout(10000) // 超时时间
                        .setFallbackOnFailure(true) // 失败后是否调用回退函数（fallback）
                        .setResetTimeout(10000) // 在开启状态下，尝试重试之前所需时间
        );
    }

    public static CircuitBreakerHandler getInstance(String circuitName,Vertx vertx){
        return new CircuitBreakerHandler(circuitName,vertx);
    }
}
