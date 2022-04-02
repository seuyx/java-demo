package com.example.redisdemo;

import com.example.redisdemo.config.redisWithExpirationSetting.CacheExpiration;
import com.example.redisdemo.service.DemoService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.util.Assert;
import redis.embedded.RedisServer;

@SpringBootTest
@ActiveProfiles("CustomRedis")
@Slf4j
public class CustomRedisTests {
    @Autowired
    private DemoService demoService;

    private static RedisServer redisServer;

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
    void testDefaultRedisCache() throws InterruptedException {
        String value = demoService.getStringWithDefaultCache(1, "dummy");
        Assert.isTrue(value.equalsIgnoreCase(String.valueOf(1)));
        log.info("Original, Given number is {}, and returned value is {}", 1, 1);

        value = demoService.getStringWithDefaultCache(2, "dummy");
        Assert.isTrue(value.equalsIgnoreCase(String.valueOf(1)));
        log.info("Hit long cache, Given number is {}, and returned value is {}", 2, 1);

        Thread.sleep(CacheExpiration.DEFAULT.getTtl() * 1000);

        value = demoService.getStringWithDefaultCache(3, "dummy");
        Assert.isTrue(value.equalsIgnoreCase(String.valueOf(3)));
        log.info("Long cache expired, Given number is {}, and returned value is {}", 3, 3);
    }

    @Test
    void testLongRedisCache() throws InterruptedException {
        String value = demoService.getStringWithLongCache(1, "dummy");
        Assert.isTrue(value.equalsIgnoreCase(String.valueOf(1)));
        log.info("Original, Given number is {}, and returned value is {}", 1, 1);

        value = demoService.getStringWithLongCache(2, "dummy");
        Assert.isTrue(value.equalsIgnoreCase(String.valueOf(1)));
        log.info("Hit long cache, Given number is {}, and returned value is {}", 2, 1);

        Thread.sleep(CacheExpiration.LONG.getTtl() * 1000);

        value = demoService.getStringWithLongCache(3, "dummy");
        Assert.isTrue(value.equalsIgnoreCase(String.valueOf(3)));
        log.info("Long cache expired, Given number is {}, and returned value is {}", 3, 3);
    }

    @Test
    void testShortRedisCache() throws InterruptedException {
        String value = demoService.getStringWithShortCache(1, "dummy");
        Assert.isTrue(value.equalsIgnoreCase(String.valueOf(1)));
        log.info("Original, Given number is {}, and returned value is {}", 1, 1);

        value = demoService.getStringWithShortCache(2, "dummy");
        Assert.isTrue(value.equalsIgnoreCase(String.valueOf(1)));
        log.info("Hit short cache, Given number is {}, and returned value is {}", 2, 1);

        Thread.sleep(CacheExpiration.SHORT.getTtl() * 1000);

        value = demoService.getStringWithShortCache(3, "dummy");
        Assert.isTrue(value.equalsIgnoreCase(String.valueOf(3)));
        log.info("Short cache expired, Given number is {}, and returned value is {}", 3, 3);
    }

}
