package com.typewars.gameserver.controller;

import com.typewars.gameserver.common.CreateGameResponse;
import com.typewars.gameserver.common.GameState;

public interface GameController {
  CreateGameResponse createGame(String nickname);
  GameState getGameState(String gameId);
  void processEnteredWord(String gameId, String word);
}
