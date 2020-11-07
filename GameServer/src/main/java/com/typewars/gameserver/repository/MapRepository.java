package com.typewars.gameserver.repository;

import com.typewars.gameserver.service.GameLogic;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Repository
public class MapRepository implements GameRepository {
  private final ConcurrentHashMap<String, GameLogic> games;

  public MapRepository() {
    games = new ConcurrentHashMap<>();
  }

  @Override
  public void save(String gameMode, GameLogic gameLogic) {
    games.put(gameLogic.getId(), gameLogic);
  }

  @Override
  public Map<String, GameLogic> getAll(String gameMode) {
    return games;
  }

  @Override
  public GameLogic get(String gameMode, String id) {
    return games.get(id);
  }

  @Override
  public void delete(String gameMode, String id) {
    games.remove(id);
  }
}
