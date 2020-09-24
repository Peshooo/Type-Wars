package com.typewars.gameserver.service;

import com.typewars.gameserver.recordstore.RecordStoreClient;
import com.typewars.gameserver.repository.GameRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SurvivalGameService extends GameService {
  public SurvivalGameService(
      @Value("${game.survival-mode.name}") String gameMode,
      GameRepository gameRepository,
      RecordStoreClient recordStoreClient) {
    super(gameMode, gameRepository, recordStoreClient);
  }

  @Override
  protected GameLogic createGameLogic(String gameId, String nickname) {
    return new SurvivalGameLogic(gameId, nickname);
  }
}
