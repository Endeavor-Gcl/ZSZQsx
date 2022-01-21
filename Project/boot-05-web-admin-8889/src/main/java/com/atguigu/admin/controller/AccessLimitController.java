package com.atguigu.admin.controller;

import com.atguigu.admin.Annotations.AccessLimit;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class AccessLimitController {

    @AccessLimit(seconds = 10, maxCount = 5, needLogin = true)
    @ResponseBody
    @RequestMapping("/test")
    public String test() {
        return "success";
    }
}

