package com.atguigu.admin;

import com.atguigu.admin.entity.RequestNameList;
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
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Slf4j
@SpringBootTest(classes = Boot05WebAdminApplication.class)
@AutoConfigureMockMvc
@WebAppConfiguration
public class QueryTest {

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

    ObjectMapper om = new ObjectMapper();
    @BeforeEach
    public void before() {
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();

    }

    @Test
    public void testLikedHistory() throws Exception {

        Map<String,Object> body = new HashMap<>();
        Set<String> s = new HashSet<>();
        s.add("user1");
        s.add("user2");
        body.put("pageNum",1);
        body.put("pageSize",2);
        body.put("queryList",s);

        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/queryBehavior/user/likedHistory")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(body))
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void testHatedHistory() throws Exception {
        Map<String,Object> body = new HashMap<>();
        Set<String> s = new HashSet<>();
        s.add("user1");
        s.add("user2");
        body.put("pageNum",1);
        body.put("pageSize",2);
        body.put("queryList",s);

        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/queryBehavior/user/hatedHistory")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(body))
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void testLikedUser() throws Exception {
        Map<String,Object> body = new HashMap<>();
        Set<String> s = new HashSet<>();
        s.add("service1");
        s.add("service2");
        body.put("pageNum",1);
        body.put("pageSize",2);
        body.put("queryList",s);

        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/queryBehavior/service/likedHistory")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(body))
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
    }

    @Test
    public void testHatedUser() throws Exception {
        Map<String,Object> body = new HashMap<>();
        Set<String> s = new HashSet<>();
        s.add("service1");
        s.add("service2");
        body.put("pageNum",1);
        body.put("pageSize",2);
        body.put("queryList",s);

        MvcResult mvcResult = mockMvc
                .perform(MockMvcRequestBuilders
                        .post("/queryBehavior/service/hatedHistory")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(om.writeValueAsString(body))
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();
        System.out.println(mvcResult.getResponse().getContentAsString());
    }
}
