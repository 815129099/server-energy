package com.test;

import com.example.demo.DemoApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = DemoApplication.class)
@SpringBootTest
public class testRedis {

    @Autowired
    StringRedisTemplate stringRedisTemplate;

    @Test
    public void testTemplate(){
        stringRedisTemplate.opsForValue().set("aaa","2112");
        System.out.println(stringRedisTemplate.opsForValue().get("aaa"));
    }
}
