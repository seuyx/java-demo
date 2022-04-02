package com.example.redisdemo.config.redisWithExpirationSetting;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.context.annotation.Profile;

@AllArgsConstructor
@Profile("CustomRedis")
public enum CacheExpiration {

    DEFAULT(20),
    LONG(30),
    SHORT(10);

    public static final String DEFAULT_TTL = "DEFAULT";
    public static final String LONG_TTL = "LONG";
    public static final String SHORT_TTL = "SHORT";

    @Getter
    private final Integer ttl;
}
