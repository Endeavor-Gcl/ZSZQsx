package com.atguigu.admin.entity;

import lombok.Data;

@Data
public class OperationStatus {
    private int responseStatus;
    private String operationName;
}
