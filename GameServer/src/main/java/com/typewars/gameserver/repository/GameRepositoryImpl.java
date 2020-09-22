package com.typewars.gameserver.repository;

import com.typewars.gameserver.service.GameLogic;
import org.springframework.data.redis.core.HashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public class GameRepositoryImpl implements GameRepository {
  private static final String HASH_NAME = "game";

  private final RedisTemplate<String, GameLogic> redisTemplate;
  private final HashOperations<String, String, GameLogic> hashOperations;

  public GameRepositoryImpl(RedisTemplate<String, GameLogic> redisTemplate) {
    this.redisTemplate = redisTemplate;
    hashOperations = redisTemplate.opsForHash();
  }

  @Override
  public void save(GameLogic gameLogic) {
    hashOperations.put(HASH_NAME, gameLogic.getId(), gameLogic);
  }

  @Override
  public Map<String, GameLogic> getAll() {
    return hashOperations.entries(HASH_NAME);
  }

  @Override
  public GameLogic get(String id) {
    return hashOperations.get(HASH_NAME, id);
  }

  @Override
  public void delete(String id) {
    hashOperations.delete(HASH_NAME, id);
  }
}
