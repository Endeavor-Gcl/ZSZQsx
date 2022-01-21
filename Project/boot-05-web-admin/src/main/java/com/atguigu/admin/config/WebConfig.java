package com.atguigu.admin.config;

import com.atguigu.admin.interceptor.ApiProtectedInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;

import javax.annotation.Resource;

@Configuration
public class WebConfig extends WebMvcConfigurerAdapter {

    @Resource
    private ApiProtectedInterceptor apiProtectedInterceptor;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(apiProtectedInterceptor);
    }


}