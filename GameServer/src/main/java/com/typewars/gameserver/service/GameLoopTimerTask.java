package com.typewars.gameserver.service;

import com.typewars.gameserver.common.GameState;
import com.typewars.gameserver.common.GameStatus;
import com.typewars.gameserver.model.GameRecord;
import com.typewars.gameserver.recordstore.RecordStoreClient;
import com.typewars.gameserver.repository.GameRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;

import java.time.OffsetDateTime;
import java.util.Timer;
import java.util.TimerTask;

public class GameLoopTimerTask extends TimerTask {
  private static final Logger logger = LoggerFactory.getLogger(GameLoopTimerTask.class);

  private static final long NOT_STARTED_EXPIRATION_TIME_MILLIS = 60000;
  private static final long DELETE_FINISHED_GAME_DELAY_MILLIS = 60000;
  private static final long DELETE_EXPIRED_GAME_DELAY_MILLIS = 0;

  private final GameRepository gameRepository;
  private final RecordStoreClient recordStoreClient;
  private final String gameMode;
  private final String gameId;

  public GameLoopTimerTask(GameRepository gameRepository, RecordStoreClient recordStoreClient, String gameMode, String gameId) {
    this.gameRepository = gameRepository;
    this.recordStoreClient = recordStoreClient;
    this.gameMode = gameMode;
    this.gameId = gameId;
  }

  @Override
  @Transactional
  public void run() {
    try {
      GameLogic gameLogic = gameRepository.get(gameMode, gameId);

      checkFinished(gameLogic);
      checkExpired(gameLogic);

      gameLogic.updateGame();
      gameRepository.save(gameMode, gameLogic);
    } catch (Exception e) {
      logger.error("Exception in thread run ", e);
    }
  }

  private void checkFinished(GameLogic gameLogic) {
    if (gameLogic.getGameStatus() == GameStatus.FINISHED) {
      afterGameFinished(gameLogic);
      cancel();
    }
  }

  private void checkExpired(GameLogic gameLogic) {
    if (gameLogic.getGameStatus() == GameStatus.NOT_STARTED
        && System.currentTimeMillis() - gameLogic.getCreatedAt() > NOT_STARTED_EXPIRATION_TIME_MILLIS) {
      scheduleDelete(DELETE_EXPIRED_GAME_DELAY_MILLIS);
      cancel();
    }
  }

  private void afterGameFinished(GameState gameState) {
    GameRecord gameRecord =
        new GameRecord(gameState.getId(), gameState.getNickname(), gameState.getScore(), OffsetDateTime.now());
    recordStoreClient.saveRecord(gameMode, gameRecord);
    scheduleDelete(DELETE_FINISHED_GAME_DELAY_MILLIS);
  }

  private void scheduleDelete(long delay) {
    Timer deleteGameTimer = new Timer();
    deleteGameTimer.schedule(new DeleteGameTimerTask(gameRepository, gameMode, gameId), delay);
  }
}
