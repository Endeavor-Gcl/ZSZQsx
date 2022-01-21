package com.atguigu.admin.Annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AccessLimit {

    // 失效时间 单位（秒）
    int seconds();

    // 最大请求次数
    int maxCount();

    // 是否需要登录
    boolean needLogin()default true;
}
