package com.quanxiaoha.xiaohashu.auth;

import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;

@SpringBootTest
public class RedisTest {

    @Resource
    RedisTemplate<String, Object> redisTemplate;

    @Test
    public void test(){
        redisTemplate.opsForValue().set("11111","test");
    }
}
