package com.atguigu.admin.controller;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.admin.bean.User;
import com.atguigu.admin.entity.NumberResponseData;
import com.atguigu.admin.entity.PageData;
import com.atguigu.admin.service.impl.*;
import com.atguigu.admin.util.RedisUnit;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;
import redis.clients.jedis.Jedis;

import java.io.IOException;
import java.util.*;
import java.util.concurrent.ExecutionException;

@RestController
public class QueryController {
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

    @Autowired
    QueryAsync queryAsync;

    Jedis jedis = new Jedis("localhost");

    public void UserData(User user,String city,int age,short sex,String user_name) {
        user.setUserAge(age);
        user.setUserSex(sex);
        user.setUserName(user_name);
    }



    @PostMapping("queryBehavior/user/likedHistory")
    public NumberResponseData queryLikedServiceNum(@RequestBody JSONObject jsonObject) throws ExecutionException, InterruptedException{
        List<String> nameL = (List<String>) jsonObject.get("queryList");
        Set<String> nameList = new HashSet<>();
        for (String name : nameL) {
            nameList.add(name);
        }
        int pageNum = (int)jsonObject.get("pageNum");
        int pageSize = (int)jsonObject.get("pageSize");


        NumberResponseData numberResponseData = queryAsync.queryNumOfLikedService(nameList,pageNum,pageSize).get();

        return numberResponseData;
    }
    @PostMapping("queryBehavior/user/hatedHistory")
    public NumberResponseData queryHatedServiceNum(@RequestBody JSONObject jsonObject) throws ExecutionException, InterruptedException{
        List<String> nameL = (List<String>) jsonObject.get("queryList");
        Set<String> nameList = new HashSet<>();
        for (String name : nameL) {
            nameList.add(name);
        }
        int pageNum = (int)jsonObject.get("pageNum");
        int pageSize = (int)jsonObject.get("pageSize");
        NumberResponseData numberResponseData = queryAsync.queryNumOfHatedService(nameList,pageNum,pageSize).get();
        return numberResponseData;
    }
    @PostMapping("queryBehavior/service/likedHistory")
    public NumberResponseData queryLikedUserNum(@RequestBody JSONObject jsonObject) throws ExecutionException, InterruptedException{
        List<String> nameL = (List<String>) jsonObject.get("queryList");
        Set<String> nameList = new HashSet<>();
        for (String name : nameL) {
            nameList.add(name);
        }
        int pageNum = (int)jsonObject.get("pageNum");
        int pageSize = (int)jsonObject.get("pageSize");
        NumberResponseData numberResponseData = queryAsync.queryNumOfLikedUser(nameList,pageNum,pageSize).get();
        return numberResponseData;
    }
    @PostMapping("queryBehavior/service/hatedHistory")
    public NumberResponseData queryHatedUserNum(@RequestBody JSONObject jsonObject) throws ExecutionException, InterruptedException{
        List<String> nameL = (List<String>) jsonObject.get("queryList");
        Set<String> nameList = new HashSet<>();
        for (String name : nameL) {
            nameList.add(name);
        }
        int pageNum = (int)jsonObject.get("pageNum");
        int pageSize = (int)jsonObject.get("pageSize");
        NumberResponseData numberResponseData = queryAsync.queryNumOfHatedUser(nameList,pageNum,pageSize).get();
        return numberResponseData;
    }


    @GetMapping("queryBehavior/user/searchLikedService")
    public Map<String, PageData<List<String>>> searchContent(@RequestParam String serviceName,@RequestParam int pageSize) throws IOException {
        Map<String, PageData<List<String>>> result = new HashMap<>();

        redisUnit.listToZset("Like","user_like");
        redisUnit.listToZset("Hate","user_hate");
        PageData<List<String>> whoLike = redisUnit.getPageData("Like",false,serviceName,pageSize);
        PageData<List<String>> whoHate = redisUnit.getPageData("Hate",false,serviceName,pageSize);
        result.put("Like" + serviceName,whoLike);
        result.put("Hate" + serviceName,whoHate);

        return result;
    }
    @GetMapping("queryBehavior/user/searchHatedService")
    public Map<String, PageData<List<String>>> searchUser(@RequestParam String userName,@RequestParam int pageSize) throws IOException {
        Map<String, PageData<List<String>>> result = new HashMap<>();

        redisUnit.listToZset("Like","user_like");
        redisUnit.listToZset("Hate","user_hate");
        PageData<List<String>> userLike = redisUnit.getPageData("Like",true,userName,pageSize);
        PageData<List<String>> userHate = redisUnit.getPageData("Hate",true,userName,pageSize);
        result.put(userName+"Like",userLike);
        result.put(userName+"Hate",userHate);

//        Cursor<Map.Entry<Object, Object>> scan_like = redisTemplate.opsForHash().scan("user_like", ScanOptions.NONE);
//        List<String> list_like = new ArrayList<>();
//        Cursor<Map.Entry<Object, Object>> scan_hate = redisTemplate.opsForHash().scan("user_hate", ScanOptions.NONE);
//        List<String> list_hate = new ArrayList<>();
//        while (scan_like.hasNext()) {
//            Map.Entry<Object, Object> entry = scan_like.next();
//            String key = (String) entry.getKey();
//            String[] split = key.split(":");
//            String user_name = split[0];
//            String service = split[1];
//            if (user_name.equals(user) && redisTemplate.opsForHash().get("user_like",key).toString().equals("0")) {
//                list_like.add(service);
//                System.out.println(key);
//            }
//        }
//        while (scan_hate.hasNext()) {
//            Map.Entry<Object, Object> entry = scan_hate.next();
//            String key = (String) entry.getKey();
//            String[] split = key.split(":");
//            String user_name = split[0];
//            String service = split[1];
//            if (user_name.equals(user) && redisTemplate.opsForHash().get("user_hate",key).toString().equals("0")) {
//                list_hate.add(service);
//                System.out.println(key);
//            }
//        }
        return result;
    }

    @GetMapping("/search")
    public Map<String, PageData<List<String>>> search(@RequestParam String name,@RequestParam int pageSize,@RequestParam boolean isSearchUser)throws IOException {
        Map<String, PageData<List<String>>> result = new HashMap<>();

        redisUnit.listToZset("Like","user_like");
        redisUnit.listToZset("Hate","user_hate");
        if (isSearchUser) {
            result = searchUser(name,pageSize);
        }
        else {
            result = searchContent(name,pageSize);
        }

        return result;
    }

}
