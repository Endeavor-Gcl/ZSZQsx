package com.atguigu.admin;

import com.atguigu.admin.mapper.UserMapper;
import com.atguigu.admin.util.RedisUnit;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.util.HashMap;
import java.util.Map;

//这里配置文件没找到，估计是test的自启动里面就引入了的，查看了下版本org.springframework:spring-test:5.2.4.RELEASE

@Slf4j
@SpringBootTest(classes = Boot05WebAdminApplication.class)
@AutoConfigureMockMvc
@WebAppConfiguration
public class WriteTest {


    @Autowired
    WebApplicationContext wac;//模拟ServletContext环境
    MockMvc mockMvc;//声明MockMvc对象

    @Autowired
    JdbcTemplate jdbcTemplate;

    @Autowired
    UserMapper userMapper;

    @Autowired
    StringRedisTemplate redisTemplate;

    @Autowired
    RedisUnit redisUnit;

    ObjectMapper objectMapper = new ObjectMapper();

    @BeforeEach
    public void before() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

    }

//    @Test
//    public void contextLoads() {
////        jdbcTemplate.queryForObject("select * from account_tbl")
////        jdbcTemplate.queryForList("select * from account_tbl",)
//        Long aLong = jdbcTemplate.queryForObject("select count(*) from user", Long.class);
//        log.info("记录总数：{}",aLong);
//        User user = userMapper.selectById(1L);
//        log.info("用户信息:{}",user);
//
//        // redisUnit.zSetGetByPage("zSetValue",1,2);
//        redisUnit.listToZset("Like","user_like");
//        redisUnit.listToZset("Hate","user_hate");
//        System.out.println(redisUnit.getPageData("Like",true,"gcl",2));
//        System.out.println(redisUnit.getPageData("Hate",true,"gcl",2));
//    }

    @Test
    public void testCancelLike() throws Exception {
        Map<String,Object> body = new HashMap<>();
        body.put("userName","user1");
        body.put("serviceName","service1");

        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/userBehavior/cancelLike")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body))
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void testCancelHate() throws Exception {
        Map<String,Object> body = new HashMap<>();
        body.put("userName","user1");
        body.put("serviceName","service1");

        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/userBehavior/cancelHate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body))
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void testLike() throws Exception {
        Map<String,Object> body = new HashMap<>();
        body.put("userName","user1");
        body.put("serviceName","service1");

        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/userBehavior/like")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body))
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void testHate() throws Exception {
        Map<String,Object> body = new HashMap<>();
        body.put("userName","user1");
        body.put("serviceName","service1");

        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/userBehavior/hate")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(body))
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void testLogin() throws Exception {
        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders
                        .get("/login")
                        .param("account","123")
                        .param("password","123456")
                        .contentType(MediaType.APPLICATION_JSON)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
    }









}
