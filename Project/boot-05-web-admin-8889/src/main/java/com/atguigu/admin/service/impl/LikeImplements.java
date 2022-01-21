package com.atguigu.admin.service.impl;

import com.atguigu.admin.entity.OperationStatus;
import com.atguigu.admin.service.Like;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.concurrent.Future;

@Service
public class LikeImplements implements Like {
    @Autowired
    StringRedisTemplate redisTemplate;

    Jedis jedis = new Jedis("localhost");

    @Async(value = "getAsyncExecutor")
    public Future<OperationStatus> testAsync(String userName, String serviceName) {
        String user_name = userName;
        String service_name = serviceName;
        String key = user_name + ":" + service_name;
        if (!redisTemplate.opsForHash().hasKey("user_like",key)) {
            redisTemplate.opsForHash().put("user_like",key,"1");
        }
        if (!redisTemplate.opsForHash().hasKey("user_hate",key)) {
            redisTemplate.opsForHash().put("user_hate",key,"1");
        }
        String status_zan = String.valueOf(redisTemplate.opsForHash().get("user_like",key));
        String status_cai = String.valueOf(redisTemplate.opsForHash().get("user_hate",key));
        String result = "逻辑错误";
        if (status_cai.equals("0")) { // have been hated
            redisTemplate.opsForHash().put("user_hate",key,"1"); // cancel hate
            redisTemplate.opsForHash().put("user_like",key,"0"); // have been liked
            result = "取消点踩，进行点赞";
        }
        else {
            if (status_zan.equals("0")) {
                redisTemplate.opsForHash().put("user_like",key,"1");
                result = "已点过赞，本次为取消点赞行为";
            }
            else {
                redisTemplate.opsForHash().put("user_like",key,"0");
                result = "未点过赞，且未点过踩，本次为点赞行为";
            }
        }
        System.out.println(result);
        OperationStatus operationStatus = new OperationStatus();
        operationStatus.setResponseStatus(200);
        operationStatus.setOperationName("Like");
        return new AsyncResult<>(operationStatus);
    }

    @Async(value = "getAsyncExecutor")
    public Future<OperationStatus> userLike(String userName, String serviceName) {
        String user_name = userName;
        String service_name = serviceName;
        String key = user_name + ":" + service_name;
        if (!redisTemplate.opsForHash().hasKey("user_like",key)) {
            redisTemplate.opsForHash().put("user_like",key,"1");
        }
        if (!redisTemplate.opsForHash().hasKey("user_hate",key)) {
            redisTemplate.opsForHash().put("user_hate",key,"1");
        }
        String status_zan = String.valueOf(redisTemplate.opsForHash().get("user_like",key));
        String status_cai = String.valueOf(redisTemplate.opsForHash().get("user_hate",key));
        String result = "逻辑错误";
        if (status_cai.equals("0")) { // have been hated
            redisTemplate.opsForHash().put("user_hate",key,"1"); // cancel hate
            redisTemplate.opsForHash().put("user_like",key,"0"); // have been liked
            result = "取消点踩，进行点赞";
        }
        else {
            if (status_zan.equals("0")) {
                redisTemplate.opsForHash().put("user_like",key,"1");
                result = "已点过赞，本次为取消点赞行为";
            }
            else {
                redisTemplate.opsForHash().put("user_like",key,"0");
                result = "未点过赞，且未点过踩，本次为点赞行为";
            }
        }
        System.out.println(result);
        OperationStatus operationStatus = new OperationStatus();
        operationStatus.setResponseStatus(200);
        operationStatus.setOperationName("Like");
        return new AsyncResult<>(operationStatus);
    }

    @Async(value = "getAsyncExecutor")
    public Future<OperationStatus> userCancelLike(String userName, String serviceName){
        String user_name = userName;
        String service_name = serviceName;
        String key = user_name + ":" + service_name;
        if (!redisTemplate.opsForHash().hasKey("user_like",key)) {
            redisTemplate.opsForHash().put("user_like",key,"1");
        }
        if (!redisTemplate.opsForHash().hasKey("user_hate",key)) {
            redisTemplate.opsForHash().put("user_hate",key,"1");
        }
        String status_zan = String.valueOf(redisTemplate.opsForHash().get("user_like",key));
        String status_cai = String.valueOf(redisTemplate.opsForHash().get("user_hate",key));
        String result = "逻辑错误";

        if (status_zan.equals("0")) {
                redisTemplate.opsForHash().put("user_like",key,"1");
                result = "已点过赞，本次为取消点赞行为";
        }
        else {
                result = "未点过赞，且未点过踩，本次为无效的取消点赞行为";
        }

        System.out.println(result);
        OperationStatus operationStatus = new OperationStatus();
        operationStatus.setResponseStatus(200);
        operationStatus.setOperationName("Cancel Like");
        return new AsyncResult<>(operationStatus);
    }
}
