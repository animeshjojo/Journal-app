package com.animesh.journal.service;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisConfigTests {

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void redisConfigTest(){
       redisTemplate.opsForValue().set("key","value");
       Object a=redisTemplate.opsForValue().get("name");
       System.out.println(a);
    }
}
