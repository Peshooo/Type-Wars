package com.typewars.gameserver.service;

import com.typewars.gameserver.common.GameState;
import com.typewars.gameserver.common.GameStatus;
import com.typewars.gameserver.model.GameRecord;
import com.typewars.gameserver.recordstore.RecordStoreClient;
import com.typewars.gameserver.repository.GameRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.OffsetDateTime;
import java.util.Timer;
import java.util.TimerTask;

public class GameLoopTimerTask extends TimerTask {
  private static final Logger logger = LoggerFactory.getLogger(GameLoopTimerTask.class);

  private static final long NOT_STARTED_EXPIRATION_TIME_MILLIS = 60000;
  private static final long DELETE_DELAY_MILLIS = 500;

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
  public void run() {
    try {
      GameLogic gameLogic = gameRepository.get(gameMode, gameId);

      checkIfFinished(gameLogic);
      checkIfExpired(gameLogic);

      gameLogic.updateGame();
      gameRepository.save(gameMode, gameLogic);
    } catch (Exception e) {
      logger.error("Exception in thread run ", e);
    }
  }

  private void checkIfFinished(GameLogic gameLogic) {
    if (gameLogic.getGameStatus() == GameStatus.FINISHED) {
      afterGameFinished(gameLogic);
      cancel();
    }
  }

  private void checkIfExpired(GameLogic gameLogic) {
    if (gameLogic.getGameStatus() == GameStatus.NOT_STARTED
        && System.currentTimeMillis() - gameLogic.getCreatedAt() > NOT_STARTED_EXPIRATION_TIME_MILLIS) {
      scheduleDelete();
      cancel();
    }
  }

  private void afterGameFinished(GameState gameState) {
    GameRecord gameRecord =
        new GameRecord(gameState.getId(), gameState.getNickname(), gameState.getScore(), OffsetDateTime.now());
    recordStoreClient.saveRecord(gameMode, gameRecord);
    scheduleDelete();
  }

  private void scheduleDelete() {
    Timer deleteGameTimer = new Timer();
    deleteGameTimer.schedule(new DeleteGameTimerTask(gameRepository, gameMode, gameId), DELETE_DELAY_MILLIS);
  }
}
