package com.example.redisdemo.config.redisWithExpirationSetting;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.cache.RedisCacheWriter;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import java.time.Duration;


@Configuration
@EnableCaching
@Profile("CustomRedis")
public class CustomRedis {

    @Bean
    public RedisCacheConfiguration redisCacheConfiguration() {
        return RedisCacheConfiguration.defaultCacheConfig()
                .entryTtl(Duration.ofSeconds(CacheExpiration.DEFAULT.getTtl()));
    }

    @Bean
    public RedisCacheWriter redisCacheWriter() {
        return RedisCacheWriter.lockingRedisCacheWriter(redisConnectionFactory());
    }

    @Bean
    public LettuceConnectionFactory redisConnectionFactory() {
        return new LettuceConnectionFactory();
    }

    @Bean
    public RedisCacheManager redisCacheManager() {
        return new CustomRedisCacheManager(redisCacheWriter(), redisCacheConfiguration());
    }
}
