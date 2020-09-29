package com.typewars.gameserver.repository;

import com.typewars.gameserver.service.GameLogic;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class RedisRepository implements GameRepository {
  private final RedisTemplate<String, GameLogic> redisTemplate;
  private final HashOperations<String, String, GameLogic> hashOperations;

  public RedisRepository(RedisTemplate<String, GameLogic> redisTemplate) {
    this.redisTemplate = redisTemplate;
    hashOperations = redisTemplate.opsForHash();
  }

  @Override
  public void save(String gameMode, GameLogic gameLogic) {
    hashOperations.put(gameMode, gameLogic.getId(), gameLogic);
  }

  @Override
  public Map<String, GameLogic> getAll(String gameMode) {
    return hashOperations.entries(gameMode);
  }

  @Override
  public GameLogic get(String gameMode, String id) {
    return hashOperations.get(gameMode, id);
  }

  @Override
  public void delete(String gameMode, String id) {
    hashOperations.delete(gameMode, id);
  }
}
