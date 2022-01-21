package com.atguigu.admin.controller;

import com.alibaba.fastjson.JSONObject;
import com.atguigu.admin.bean.User;
import com.atguigu.admin.entity.OperationStatus;
import com.atguigu.admin.mapper.UserMapper;
import com.atguigu.admin.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequestMapping("/login")
public class LoginController {
    @Autowired
    UserServiceImpl userService;

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    UserMapper userMapper;

    @PostMapping("/register")
    public void userRegister(@RequestBody JSONObject jsonObject) {
        String userName = jsonObject.getString("userName");
        String password = jsonObject.getString("password");
        User user = new User();
        user.setName(userName);
        user.setPassword(password);
        System.out.println("注册成功");
        userMapper.insert(user);
    }
    @PostMapping("/deleteUser")
    public void userDelete(@RequestBody JSONObject jsonObject) {

    }

    @PostMapping("/userLogin")
    public OperationStatus userLogin(HttpServletRequest request, @RequestBody JSONObject jsonObject) {
        String userName = jsonObject.getString("userName");
        String password = jsonObject.getString("password");
        OperationStatus status = new OperationStatus();
        status.setOperationName("user login success");
        status.setResponseStatus(0);
        List<User> userList = userService.list();
        for (User user : userList) {
            if (user.getName() != null && user.getName().equals(userName) && user.getPassword() != null
            && user.getPassword().equals(password)) {
                status.setResponseStatus(200);
                break;
            }
        }
        if (status.getResponseStatus() == 0) {
            status.setOperationName("user login failed");
            status.setResponseStatus(404); // 代表未找到该用户，说明未注册
        }

        if (status.getResponseStatus() == 200) {
            redisTemplate.opsForHash().put("userLogin",userName,"login");
        }

        return status;
    }

    @PostMapping("/loginOut")
    public void userLoginOut(@RequestBody JSONObject jsonObject) {

    }
}
