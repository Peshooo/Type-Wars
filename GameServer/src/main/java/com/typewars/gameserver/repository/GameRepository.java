package com.typewars.gameserver.repository;

import com.typewars.gameserver.service.GameLogic;

import java.util.Map;

public interface GameRepository {
  void save(GameLogic gameLogic);
  Map<String, GameLogic> getAll();
  GameLogic get(String id);
  void delete(String id);
}
