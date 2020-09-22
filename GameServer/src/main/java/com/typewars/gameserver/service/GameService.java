package com.typewars.gameserver.service;

import com.typewars.gameserver.common.GameState;
import com.typewars.gameserver.common.GameStatus;
import com.typewars.gameserver.model.SurvivalRecord;
import com.typewars.gameserver.recordstore.RecordStoreClient;
import com.typewars.gameserver.repository.GameRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.Timer;
import java.util.TimerTask;
import java.util.UUID;

@Service
public class GameService {
  private final Logger logger = LoggerFactory.getLogger(getClass());

  private final GameRepository gameRepository;
  private final RecordStoreClient recordStoreClient;

  public GameService(GameRepository gameRepository, RecordStoreClient recordStoreClient) {
    this.gameRepository = gameRepository;
    this.recordStoreClient = recordStoreClient;
  }

  public String createGame(String nickname) {
    String gameId = generateUuid();
    GameLogic gameLogic = new GameLogic(gameId, nickname);
    gameRepository.save(gameLogic);
    startGameLoop(gameId);

    return gameId;
  }

  private String generateUuid() {
    return UUID.randomUUID().toString();
  }

  private void startGameLoop(String gameId) {
    Timer timer = new Timer();
    timer.scheduleAtFixedRate(new TimerTask() {
      @Override
      public void run() {
        try {
          GameLogic gameLogic = gameRepository.get(gameId);

          if (gameLogic.getGameStatus() == GameStatus.FINISHED) {
            afterGameFinished(gameLogic);
            cancel();
          }

          gameLogic.updateGame();

          gameRepository.save(gameLogic);
        } catch (Exception e) {
          logger.info("Exception in thread run ", e);
        }
      }
    }, 32, 32);
  }

  private void afterGameFinished(GameState gameState) {
    SurvivalRecord survivalRecord =
        new SurvivalRecord(gameState.getId(), gameState.getNickname(), gameState.getScore(), OffsetDateTime.now());
    recordStoreClient.saveSurvivalRecord(survivalRecord);
  }

  public GameState getGameState(String gameId) {
    return gameRepository.get(gameId);
  }

  public void processEnteredWord(String gameId, String word) {
    GameLogic gameLogic = gameRepository.get(gameId);
    gameLogic.enterWord(word);
    gameRepository.save(gameLogic);
  }
}
