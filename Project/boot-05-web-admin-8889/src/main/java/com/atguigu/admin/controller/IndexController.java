package com.atguigu.admin.controller;
import com.atguigu.admin.bean.User;
import com.atguigu.admin.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
public class IndexController {
    @Autowired
    UserServiceImpl userService;

    @GetMapping(value = {"/"})
    public String loginPage() {
        return "main";
    }


    @ResponseBody
    @RequestMapping(value = "/setSession",method = { RequestMethod.POST, RequestMethod.GET })
    public  String setCookies(HttpServletRequest request){
        HttpSession session = request.getSession();
        session.setAttribute("data", "微信公众号：骄傲的程序员");
        return "添加成功";
    }


    @ResponseBody
    @RequestMapping(value = "/getSession",method = { RequestMethod.POST, RequestMethod.GET })
    public String getCookies(HttpServletRequest request){
        HttpSession session = request.getSession();
        String data = (String) session.getAttribute("data");
        return data;
    }

    @ResponseBody
    @GetMapping("/user/delete/{userName}")
    public String deleteUser(@PathVariable String userName){
        List<User> user = userService.list();
        for (User us : user){
            if (us.getName() != null && us.getName().equals(userName)) {
                return "find" + userName;
            }
        }

        return "不能find"+userName;
    }


}
