package com.atguigu.admin.service;

import com.atguigu.admin.entity.NumberResponseData;

import java.util.Set;
import java.util.concurrent.Future;

public interface QueryNumOfLikedUser {
    public NumberResponseData queryNumOfLikedUser(Set<String> serviceName, int pageNum, int pageSize);
}
