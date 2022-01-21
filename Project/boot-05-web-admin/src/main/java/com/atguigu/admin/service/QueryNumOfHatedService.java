package com.atguigu.admin.service;

import com.atguigu.admin.entity.NumberResponseData;
import com.atguigu.admin.entity.OperationStatus;

import java.util.Set;
import java.util.concurrent.Future;

public interface QueryNumOfHatedService {
    public NumberResponseData queryNumOfHatedService(Set<String> serviceName, int pageNum, int pageSize);
}
