package com.atguigu.admin.entity;

import lombok.Data;
import org.thymeleaf.expression.Lists;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Data
public class PageData<T> implements Serializable{
    /** 数据集合 */
    protected List<T> result = new ArrayList();
    /** 数据总数 */
    protected int totalCount = 0;
    /** 总页数 */
    protected long pageCount = 0;
    /** 每页记录 */
    protected int pageSize = 5;
    /** 初始页 */
    protected int pageBegin = 1;
}

