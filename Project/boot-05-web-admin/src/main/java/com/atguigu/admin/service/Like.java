package com.atguigu.admin.service;

import com.atguigu.admin.entity.OperationStatus;

import java.util.concurrent.Future;

public interface Like {

    public Future<OperationStatus> userLike(String userName, String serviceName);
    public Future<OperationStatus> userCancelLike(String userName, String serviceName);
}
