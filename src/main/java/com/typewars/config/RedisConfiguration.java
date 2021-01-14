package com.typewars.config;

import com.typewars.model.RedisGame;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
@Profile("redis")
public class RedisConfiguration {
    @Bean
    public RedisTemplate<String, RedisGame> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<String, RedisGame> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(connectionFactory);
        redisTemplate.setEnableTransactionSupport(true);

        return redisTemplate;
    }
}
