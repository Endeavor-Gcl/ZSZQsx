package com.atguigu.admin.bean;


import com.atguigu.admin.dao.UserTypeHandler;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
@TableName(autoResultMap = true)
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
    @TableField(typeHandler = UserTypeHandler.class)
    private String password;
}