package com.typewars.gameserver.config;

import com.typewars.gameserver.service.GameLogic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

@Configuration
public class RedisConfig {
  @Bean
  public JedisConnectionFactory jedisConnectionFactory() {
    return new JedisConnectionFactory();
  }

  @Bean
  public RedisTemplate<String, GameLogic> redisTemplate(JedisConnectionFactory jedisConnectionFactory) {
    RedisTemplate<String, GameLogic> redisTemplate = new RedisTemplate<>();
    redisTemplate.setConnectionFactory(jedisConnectionFactory);

    return redisTemplate;
  }
}
