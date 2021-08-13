/*
 * Copyright (c) 2021. 四川高诚物联网科技有限公司.  All rights reserved.
 * 本软件基于Apache License 2.0
 * 任何单位及个人可以用于修改，分发及商业目的
 */

package org.isdp.vertx.boot.annotation;


import java.lang.annotation.*;

/**
 *  注入配置文件信息
 */
@Target({ ElementType.TYPE, ElementType.METHOD })
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ConfigurationProperties {

    /**
     * 指定的配置项前缀
     */
    String value() default "";

    /**
     * 指定的配置项前缀
     */
    String prefix() default "";

    /**
     * 是否忽略无效的字段
     */
    boolean ignoreInvalidFields() default false;

    /**
     * 是否忽略不知道的字段
     */
    boolean ignoreUnknownFields() default true;
}
