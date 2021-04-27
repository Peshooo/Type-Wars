package com.typewars.service.game;

import com.typewars.service.finishedgames.FinishedSurvivalGamesService;
import com.typewars.service.gamesmanager.GamesManager;
import org.springframework.stereotype.Service;

@Service
public class SurvivalGameService extends GameService {
    public SurvivalGameService(
            GamesManager gamesManager,
            FinishedSurvivalGamesService finishedSurvivalGamesService) {
        super(gamesManager, finishedSurvivalGamesService);
    }

    @Override
    protected Game createGame(String gameId, String nickname) {
        return new SurvivalGame(gameId, nickname);
    }
}
