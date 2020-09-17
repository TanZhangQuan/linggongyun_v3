package com.example.merchant.interceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用来确认用户是否登录
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginRequired {

    // 是否进行校验
    boolean required() default true;

//    // 默认管理员
//    RoleEnum role() default RoleEnum.ADMIN;
}