package com.atguigu.admin.service.impl;

import com.atguigu.admin.entity.OperationStatus;
import com.atguigu.admin.service.Hate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;

import java.util.concurrent.Future;

@Service
public class HateImplements implements Hate {
    @Autowired
    StringRedisTemplate redisTemplate;

    Jedis jedis = new Jedis("localhost");

    @Async(value = "getAsyncExecutor")
    public Future<OperationStatus> userHate(String userName, String serviceName) {

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
        if (status_zan.equals("0")) { // have been liked
            redisTemplate.opsForHash().put("user_like",key,"1"); // cancel like
            redisTemplate.opsForHash().put("user_hate",key,"0"); // have been hated
            result = "取消点赞，进行点踩";
        }
        else {
            if (status_cai.equals("0")) {
                redisTemplate.opsForHash().put("user_hate", key, "1");
                result = "已点过踩，本次为取消点踩行为";
            } else {
                redisTemplate.opsForHash().put("user_hate", key, "0");
                result = "未点过赞，且未点过踩，本次为点踩行为";
            }
        }
        System.out.println(result);
        OperationStatus operationStatus = new OperationStatus();
        operationStatus.setResponseStatus(200);
        operationStatus.setOperationName("Hate");
        return new AsyncResult<>(operationStatus);
    }

    @Async(value = "getAsyncExecutor")
    public Future<OperationStatus> userCancelHate(String userName, String serviceName){
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

        if (status_cai.equals("0")) {
                redisTemplate.opsForHash().put("user_hate", key, "1");
                result = "已点过踩，本次为取消点踩行为";
        } else {
                result = "未点过赞，且未点过踩，本次为无效的取消点踩行为";
            }
        System.out.println(result);
        OperationStatus operationStatus = new OperationStatus();
        operationStatus.setResponseStatus(200);
        operationStatus.setOperationName("Cancel Hate");
        return new AsyncResult<>(operationStatus);
    }
}
