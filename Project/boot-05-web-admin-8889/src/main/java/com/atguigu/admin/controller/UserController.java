package com.atguigu.admin.controller;

import com.atguigu.admin.bean.Book;
import com.atguigu.admin.bean.UserEvaluate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;


@RestController
@RequestMapping("/u")
public class UserController {
    @Autowired
    StringRedisTemplate redisTemplate;

    Jedis jedis = new Jedis("localhost");

    @GetMapping("/hello")
    public String hello(String name){
        return "Hello "+ name+ " !";
    }
    @PostMapping("/book")
    public String addBook(@RequestBody Book book){
        return book.toString();
    }
    @PostMapping("/test")
    public UserEvaluate add(@RequestBody UserEvaluate userEvaluate){

        return userEvaluate;
    }



}