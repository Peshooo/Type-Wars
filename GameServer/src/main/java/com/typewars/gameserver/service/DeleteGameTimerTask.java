package com.typewars.gameserver.service;

import com.typewars.gameserver.repository.GameRepository;

import java.util.TimerTask;

public class DeleteGameTimerTask extends TimerTask {
  private final GameRepository gameRepository;
  private final String gameMode;
  private final String gameId;

  public DeleteGameTimerTask(GameRepository gameRepository, String gameMode, String gameId) {
    this.gameRepository = gameRepository;
    this.gameMode = gameMode;
    this.gameId = gameId;
  }

  @Override
  public void run() {
    gameRepository.delete(gameMode, gameId);
  }
}
