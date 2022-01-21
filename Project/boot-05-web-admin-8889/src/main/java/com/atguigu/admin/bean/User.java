package com.atguigu.admin.bean;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import lombok.Data;

@Data
public class User{
    @TableField(exist = false)
    private String userName;
    @TableField(exist = false)
    private Integer userAge;
    @TableField(exist = false)
    private Short userSex;

    //以下是数据库字段
    private Long id;
    private String name;
    private String password;
}
