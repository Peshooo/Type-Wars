package com.typewars.gameserver.repository;

import com.typewars.gameserver.service.GameLogic;

import java.util.Map;

public interface GameRepository {
  void save(String gameMode, GameLogic gameLogic);
  Map<String, GameLogic> getAll(String gameMode);
  GameLogic get(String gameMode, String id);
  void delete(String gameMode, String id);
}
