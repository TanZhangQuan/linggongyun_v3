package com.example.paas.interceptor;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface LoginRequired {

    // 是否进行校验
    boolean required() default true;

//    // 默认管理员
//    RoleEnum role() default RoleEnum.ADMIN;
}