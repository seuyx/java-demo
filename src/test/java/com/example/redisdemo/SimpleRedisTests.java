package com.example.redisdemo;

import com.example.redisdemo.service.DemoService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.Assert;
import redis.embedded.RedisServer;

@SpringBootTest
@ActiveProfiles("SimpleRedis")
@Slf4j
class SimpleRedisTests {
    @Autowired
    private DemoService demoService;

    private static RedisServer redisServer;

    @Value("${spring.redis.ttl}")
    private int timeoutInSeconds;

    @BeforeAll
    static void startRedis() {
        redisServer = RedisServer.builder()
                .port(6379)
                .setting("maxmemory 128M") //maxheap 128M
                .build();

        redisServer.start();
    }

    @AfterAll
    static void stopRedis() {
        redisServer.stop();
    }

    @Test
    void testRedisCache() throws InterruptedException {
        String value = demoService.getString(1, "dummy");
        Assert.isTrue(value.equalsIgnoreCase(String.valueOf(1)));
        log.info("Given number is {}, and returned value is {}", 1, 1);

        value = demoService.getString(2, "dummy");
        Assert.isTrue(value.equalsIgnoreCase(String.valueOf(1)));
        log.info("Given number is {}, and returned value is {}", 2, 1);

        Thread.sleep(timeoutInSeconds * 1000);

        value = demoService.getString(3, "dummy");
        Assert.isTrue(value.equalsIgnoreCase(String.valueOf(3)));
        log.info("Given number is {}, and returned value is {}", 3, 3);
    }

}
