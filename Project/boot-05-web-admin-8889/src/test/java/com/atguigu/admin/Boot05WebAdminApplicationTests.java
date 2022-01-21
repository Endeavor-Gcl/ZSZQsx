package com.atguigu.admin;

import com.atguigu.admin.bean.User;
import com.atguigu.admin.controller.QueryController;
import com.atguigu.admin.mapper.UserMapper;
import com.atguigu.admin.service.UserService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;
import java.util.List;

@Slf4j
@SpringBootTest(classes = Boot05WebAdminApplication.class)
@DisplayName("Junit5测试学习")
class Boot05WebAdminApplicationTests {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    DataSource dataSource;

    @Autowired
    UserMapper userMapper;

    @Autowired
    UserService userService;

//    @Test
//    void contextLoads() {
//    }

    @Test
    void testRedis(){
        QueryController redis = new QueryController();

        Long aLong = jdbcTemplate.queryForObject("select count(*) from user", Long.class);
        log.info("记录总数：{}",aLong);
        log.info("数据源类型：{}",dataSource.getClass());
    }

    @Test
    void testUserService(){
        User user = userMapper.selectById(1);
        log.info("用户信息：{}",user);
        List<User> list = userService.list();
        User user1 = new User();
        user1.setId((long)7);
        userService.save(user1);
        log.info("所有数据：{}",list.size());
    }

}
