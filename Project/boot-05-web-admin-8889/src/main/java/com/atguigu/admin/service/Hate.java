package com.atguigu.admin.service;


import com.atguigu.admin.entity.OperationStatus;

import java.util.concurrent.Future;

public interface Hate {
    public Future<OperationStatus> userHate(String userName, String serviceName);
    public Future<OperationStatus> userCancelHate(String userName, String serviceName);
}
