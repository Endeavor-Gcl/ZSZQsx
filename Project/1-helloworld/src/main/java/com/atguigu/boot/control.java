package com.atguigu.boot;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class control {
    
    @RequestMapping("/hello1")
    public String handle01() {
        return "hello, spring boot2";
    }
}
