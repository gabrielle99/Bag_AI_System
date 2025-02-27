package com.tianci.common.redis;

import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@PropertySource(value = "classpath:redis.properties")
@ConfigurationProperties(prefix = "spring.redis")
public class RedisConfiguration extends RedisAutoConfiguration {
}
