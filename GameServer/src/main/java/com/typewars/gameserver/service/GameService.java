package com.typewars.gameserver.service;

import com.typewars.gameserver.common.GameState;
import com.typewars.gameserver.recordstore.RecordStoreClient;
import com.typewars.gameserver.repository.GameRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Timer;
import java.util.UUID;

public abstract class GameService {
  private static final long GAME_LOOP_PERIOD_MILLIS = 32;

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
    Timer gameLoopTimer = new Timer();
    gameLoopTimer.scheduleAtFixedRate(new GameLoopTimerTask(gameRepository, recordStoreClient, gameMode, gameId), 0, GAME_LOOP_PERIOD_MILLIS);
  }

  public GameState getGameState(String gameId) {
    return gameRepository.get(gameMode, gameId);
  }

  @Transactional
  public void processEnteredWord(String gameId, String word) {
    GameLogic gameLogic = gameRepository.get(gameMode, gameId);
    gameLogic.enterWord(word);
    gameRepository.save(gameMode, gameLogic);
  }
}
