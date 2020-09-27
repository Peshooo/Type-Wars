package com.typewars.gameserver.service;

import com.typewars.gameserver.repository.GameRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.TimerTask;

public class DeleteGameTimerTask extends TimerTask {
  private static final Logger logger = LoggerFactory.getLogger(GameLoopTimerTask.class);

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
    try {
      gameRepository.delete(gameMode, gameId);
    } catch(Exception e) {
      logger.error("Exception in thread run ", e);
    }
  }
}
