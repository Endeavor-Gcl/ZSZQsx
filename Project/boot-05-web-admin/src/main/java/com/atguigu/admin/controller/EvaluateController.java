package com.atguigu.admin.controller;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.admin.bean.User;
import com.atguigu.admin.bean.UserOption;
import com.atguigu.admin.entity.NumberResponseData;
import com.atguigu.admin.entity.OperationStatus;
import com.atguigu.admin.entity.PageData;
import com.atguigu.admin.entity.RequestNameList;
import com.atguigu.admin.service.*;
import com.atguigu.admin.service.impl.*;
import com.atguigu.admin.util.RedisUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;

@RestController
public class EvaluateController {
    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    RedisUnit redisUnit;

    @Autowired
    LikeImplements likeImplements;
    @Autowired
    HateImplements hateImplements;
    @Autowired
    QueryNumOfLikedServiceImplements queryNumOfLikedServiceImplements;
    @Autowired
    QueryNumOfHatedServiceImplements queryNumOfHatedServiceImplements;
    @Autowired
    QueryHatedUserNumImplements queryHatedUserNumImplements;
    @Autowired
    QueryLikedUserNumImplements queryLikedUserNumImplements;

    Jedis jedis = new Jedis("localhost");

    public void UserData(User user,String city,int age,short sex,String user_name) {
        user.setUserAge(age);
        user.setUserSex(sex);
        user.setUserName(user_name);
    }

    @PostMapping("test")
    public OperationStatus test(@RequestBody JSONObject jsonObject) throws ExecutionException, InterruptedException {

        String userName = jsonObject.getString("userName");
        String serviceName = jsonObject.getString("serviceName");
        OperationStatus operationStatus = likeImplements.testAsync(userName,serviceName).get();

        return operationStatus;
    }

    @PostMapping("userBehavior/like")
    public OperationStatus zanOption(@RequestBody JSONObject jsonObject) throws ExecutionException, InterruptedException {
        String userName = jsonObject.getString("userName");
        String serviceName = jsonObject.getString("serviceName");
        OperationStatus operationStatus = likeImplements.userLike(userName,serviceName).get();
        return operationStatus;
    }
    @PostMapping("userBehavior/cancelLike")
    public OperationStatus zanCancelOption(@RequestBody JSONObject jsonObject) throws ExecutionException, InterruptedException {
        String userName = jsonObject.getString("userName");
        String serviceName = jsonObject.getString("serviceName");
        OperationStatus operationStatus = likeImplements.userCancelLike(userName,serviceName).get();
        return operationStatus;
    }
    @PostMapping("userBehavior/hate")
    public OperationStatus caiOption(@RequestBody JSONObject jsonObject) throws ExecutionException, InterruptedException {
        String userName = jsonObject.getString("userName");
        String serviceName = jsonObject.getString("serviceName");
        OperationStatus operationStatus = hateImplements.userHate(userName,serviceName).get();
        return operationStatus;
    }
    @PostMapping("userBehavior/cancelHate")
    public OperationStatus caiCancelOption(@RequestBody JSONObject jsonObject) throws ExecutionException, InterruptedException {
        String userName = jsonObject.getString("userName");
        String serviceName = jsonObject.getString("serviceName");
        OperationStatus operationStatus = hateImplements.userCancelHate(userName,serviceName).get();
        return operationStatus;
    }
}
