package com.typewars.service.game;

import com.typewars.service.finishedgames.FinishedStandardGamesService;
import com.typewars.service.gamesmanager.GamesManager;
import org.springframework.stereotype.Service;

@Service
public class StandardGameService extends GameService {
    public StandardGameService(
            GamesManager gamesManager,
            FinishedStandardGamesService finishedStandardGamesService) {
        super(gamesManager, finishedStandardGamesService);
    }

    @Override
    protected Game createGame(String gameId, String nickname) {
        return new StandardGame(gameId, nickname);
    }
}