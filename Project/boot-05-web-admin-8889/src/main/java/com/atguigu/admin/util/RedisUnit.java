package com.atguigu.admin.util;

import com.atguigu.admin.entity.PageData;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.Cursor;
import org.springframework.data.redis.core.ScanOptions;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import redis.clients.jedis.Jedis;
import com.alibaba.fastjson.JSON;
import java.util.*;

@Service
public class RedisUnit {
    Jedis jedis = new Jedis("localhost");

    @Autowired
    StringRedisTemplate redisTemplate;


    /**
     * 分页查询zset数据，zset的数据score需要是从1开始递增
     *
     * @param key
     * @param pageNum
     * @param pageSize
     * @return
     */
    public Set zSetGetByPage(String key, int pageNum, int pageSize) {

        try {
            if (redisTemplate.hasKey("zSetValue")) {
                int start = (pageNum - 1) * pageSize;
                int end = pageNum * pageSize - 1;
                Long size = redisTemplate.opsForZSet().size(key);
                if (end > size) {
                    end = -1;
                }
                return redisTemplate.opsForZSet().range(key, start, end);
            } else {
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    public void listToZset(String ZSetkey, String hashkey) {
        try {
            if (redisTemplate.hasKey(ZSetkey)) {
                redisTemplate.delete(ZSetkey);
            }
            int score = 1;
            Cursor<Map.Entry<Object, Object>> scan_like = redisTemplate.opsForHash().scan(hashkey, ScanOptions.NONE);
            while (scan_like.hasNext()) {
                Map.Entry<Object, Object> entry = scan_like.next();
                String key = (String) entry.getKey();
                String[] split = key.split(":");
                String user_name = split[0];
                String service = split[1];
                String status = entry.getValue().toString();
                if (status.equals("0")) {
                    redisTemplate.opsForZSet().add(ZSetkey,key,score);
                    ++score;
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public PageData<List<String>> getPageData(String zSetKey,boolean isSearchUserPrefer,String name,int pageSize) {
        Set<String> result = redisTemplate.opsForZSet().range(zSetKey,0,-1);
//        System.out.println(result);
        PageData<List<String>> pageData = new PageData<>();
        pageData.setPageSize(pageSize);

        List<List<String>> list = new ArrayList<>();
        List<String> subList = new ArrayList<>();
        int size = pageSize;
        int totalCount = 0;
        for (String hashkey : result) {
                String[] split = hashkey.split(":");
                String user_name = split[0];
                String service = split[1];
                if (!name.equals(user_name) && !name.equals(service)) {
                    continue;
                }
                if (size == 0) {
                    size = pageSize;
                    list.add(subList);
                    subList = new ArrayList<>();
                }

                if (isSearchUserPrefer) {
                    subList.add(service);
                } else {
                    subList.add(user_name);
                }
                --size;
                ++totalCount;
        }
        System.out.println(subList);
        System.out.println(size);
        if (size != pageSize) {
                list.add(subList);
                System.out.println("add");
        }

        pageData.setPageCount((int)Math.ceil(totalCount/pageSize));
        pageData.setTotalCount(totalCount);
        pageData.setResult(list);
        return pageData;
    }


}
