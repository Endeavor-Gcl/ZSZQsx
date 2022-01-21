package com.atguigu.admin.service;

import com.atguigu.admin.entity.NumberResponseData;

import java.util.Set;
import java.util.concurrent.Future;

public interface QueryNumOfLikedService {
    public NumberResponseData queryNumOfLikedService(Set<String> userName, int pageNum, int pageSize);
}
