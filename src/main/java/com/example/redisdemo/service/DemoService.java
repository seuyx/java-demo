package com.example.redisdemo.service;

import com.example.redisdemo.config.redisWithExpirationSetting.CacheExpiration;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

@Service
public class DemoService {

    @Cacheable(value = CacheExpiration.DEFAULT_TTL, key = "#key")
    public String getString(Integer number, String key) {
        return number.toString();
    }

    @Cacheable(value = CacheExpiration.DEFAULT_TTL, key = "#key")
    public String getStringWithDefaultCache(Integer number, String key) {
        return number.toString();
    }

    @Cacheable(value = CacheExpiration.LONG_TTL, key = "#key")
    public String getStringWithLongCache(Integer number, String key) {
        return number.toString();
    }

    @Cacheable(value = CacheExpiration.SHORT_TTL, key = "#key")
    public String getStringWithShortCache(Integer number, String key) {
        return number.toString();
    }
}
