package com.atguigu.admin.service;

import com.atguigu.admin.entity.NumberResponseData;

import java.util.Set;
import java.util.concurrent.Future;

public interface QueryNumOfHatedUser {
    public NumberResponseData queryNumOfHatedUser(Set<String> serviceName, int pageNum, int pageSize);
}
