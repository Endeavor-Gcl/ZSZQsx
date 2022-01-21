package com.atguigu.admin.service.impl;

import com.atguigu.admin.entity.NumberResponseData;
import com.atguigu.admin.service.QueryNumOfHatedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Service
public class QueryHatedUserNumImplements implements QueryNumOfHatedUser {
    @Autowired
    StringRedisTemplate redisTemplate;

    public NumberResponseData queryNumOfHatedUser(Set<String> serviceName, int pageNum, int pageSize){
        Cursor<Map.Entry<Object, Object>> scan_like = redisTemplate.opsForHash().scan("user_hate", ScanOptions.NONE);
        Map<String,Integer> allData = new HashMap<>();
        for (String s:serviceName) {
            allData.put(s,0);
        }
        while (scan_like.hasNext()) {
            Map.Entry<Object, Object> entry = scan_like.next();
            String key = (String) entry.getKey();
            String[] split = key.split(":");
            String user_name = split[0];
            String service = split[1];
            if (allData.containsKey(service) && redisTemplate.opsForHash().get("user_hate",key).toString().equals("0")) {
                allData.put(service,allData.get(service)+1);
            }
        }
        int begin = (pageNum-1)*pageSize+1;
        int end = pageNum*pageSize;
        int index = 1;
        Set<String> key = allData.keySet();
        Map<String,Integer> result = new HashMap<>();
        for (String k : key) {
            if (index >= begin && index <= end) {
                result.put(k,allData.get(k));
            }
            ++index;
        }
        NumberResponseData numberResponseData = new NumberResponseData();
        numberResponseData.setNumber(result);
        numberResponseData.setResponseStatus(200);
        numberResponseData.setTotalCount(allData.size());
        numberResponseData.setPageSize(pageSize);
        numberResponseData.setPageCurrent(pageNum);
        numberResponseData.setPageCount((int)Math.ceil(allData.size())/pageSize);
        numberResponseData.setOperationName("Query number of user who hate this service");
        numberResponseData.setTarget("User number");
        return numberResponseData;
    }
}
