package com.example.redisdemo.config.redisWithExpirationSetting;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.cache.RedisCache;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;

import java.time.Duration;

/**
 * @Description RedisCacheManager
 * @Author huangd
 * @Date 2019-12-31 16:09
 **/
@Slf4j
@Profile("CustomRedis")
public class CustomRedisCacheManager extends RedisCacheManager {

    public CustomRedisCacheManager(RedisCacheWriter cacheWriter, RedisCacheConfiguration defaultCacheConfiguration) {
        super(cacheWriter, defaultCacheConfiguration);
    }

    /**
     * createRedisCache
     * @param name
     * @param cacheConfig
     * @return RedisCache
     */
    @Override
    protected RedisCache createRedisCache(String name, RedisCacheConfiguration cacheConfig) {
        CacheExpiration expiration = CacheExpiration.fromCacheName(name);

        return super.createRedisCache(name, cacheConfig.entryTtl(Duration.ofSeconds(expiration.getTtl())));
    }
}