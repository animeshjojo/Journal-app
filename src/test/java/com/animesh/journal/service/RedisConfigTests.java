package com.animesh.journal.service;

import com.animesh.journal.Entity.User;
import com.animesh.journal.api.response.WeatherResponse;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisConfigTests {

    @Autowired
    private RedisTemplate redisTemplate;

    @Disabled
    @Test
    public void redisConfigTest(){
        User user=new User();
        user.setUserName("user1");
       redisTemplate.opsForValue().set("key","Value");
       Object a=redisTemplate.opsForValue().get("key");
       System.out.println(a.toString());
    }
}
