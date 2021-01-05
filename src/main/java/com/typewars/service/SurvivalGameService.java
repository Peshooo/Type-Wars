package com.typewars.service;

import com.typewars.dao.SurvivalRecordsDao;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class SurvivalGameService extends GameService {
    public SurvivalGameService(
            @Value("${game.survival-mode.name}") String gameMode,
            GamesManager gamesManager,
            SurvivalRecordsDao survivalRecordsDao) {
        super(gameMode, gamesManager, survivalRecordsDao);
    }

    @Override
    protected Game createGame(String gameId, String nickname) {
        return new SurvivalGame(gameId, nickname);
    }
}
