package com.atguigu.admin.entity;

import lombok.Data;

import java.util.List;
import java.util.Map;
import java.util.Set;

@Data
public class NumberResponseData {
    private int responseStatus;
    private String operationName;
    private String target;
    private Map<String, Integer> number;
    /**总个数*/
    protected int totalCount = 0;
    /** 总页数 */
    protected long pageCount = 0;
    /** 每页记录 */
    protected int pageSize = 5;
    /** 初始页 */
    protected int pageBegin = 1;
    protected int pageCurrent = 1;

}
