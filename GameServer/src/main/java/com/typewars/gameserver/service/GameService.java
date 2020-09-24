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
import java.util.UUID;

public abstract class GameService {
  private final Logger logger = LoggerFactory.getLogger(getClass());

  private final String gameMode;
  private final GameRepository gameRepository;
  private final RecordStoreClient recordStoreClient;

  public GameService(String gameMode, GameRepository gameRepository, RecordStoreClient recordStoreClient) {
    this.gameMode = gameMode;
    this.gameRepository = gameRepository;
    this.recordStoreClient = recordStoreClient;
  }

  public String createGame(String nickname) {
    String gameId = generateUuid();
    GameLogic gameLogic = createGameLogic(gameId, nickname);
    gameRepository.save(gameMode, gameLogic);
    startGameLoop(gameId);

    return gameId;
  }

  private String generateUuid() {
    return UUID.randomUUID().toString();
  }

  protected abstract GameLogic createGameLogic(String gameId, String nickname);

  private void startGameLoop(String gameId) {
    Timer timer = new Timer();
    timer.scheduleAtFixedRate(new TimerTask() {
      @Override
      public void run() {
        try {
          GameLogic gameLogic = gameRepository.get(gameMode, gameId);

          if (gameLogic.getGameStatus() == GameStatus.FINISHED) {
            afterGameFinished(gameLogic);
            cancel();
          }

          gameLogic.updateGame();

          gameRepository.save(gameMode, gameLogic);
        } catch (Exception e) {
          logger.info("Exception in thread run ", e);
        }
      }
    }, 32, 32);
  }

  private void afterGameFinished(GameState gameState) {
    GameRecord gameRecord =
        new GameRecord(gameState.getId(), gameState.getNickname(), gameState.getScore(), OffsetDateTime.now());
    recordStoreClient.saveRecord(gameMode, gameRecord);
  }

  public GameState getGameState(String gameId) {
    return gameRepository.get(gameMode, gameId);
  }

  public void processEnteredWord(String gameId, String word) {
    GameLogic gameLogic = gameRepository.get(gameMode, gameId);
    gameLogic.enterWord(word);
    gameRepository.save(gameMode, gameLogic);
  }
}
