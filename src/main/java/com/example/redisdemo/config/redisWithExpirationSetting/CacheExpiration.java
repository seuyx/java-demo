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

    public static final String DEFAULT_TTL = "default";
    public static final String LONG_TTL = "long";
    public static final String SHORT_TTL = "short";

    @Getter
    private Integer ttl;

    public static CacheExpiration fromCacheName(String name) {
        for (var item: CacheExpiration.values()) {
            if (item.name().equalsIgnoreCase(name)) {
                return item;
            }
        }

        return DEFAULT;
    }
}
