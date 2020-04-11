package com.aiyo.basic.common.annotation;

import java.lang.annotation.*;

/**
 * 自定义权限拦截注解，配合aop进行权限控制
 */

@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
public @interface RequiredPermission {
    String value();
}
