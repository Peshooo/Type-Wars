package com.typewars.service.game;

import com.typewars.service.finishedgames.FinishedStandardGamesService;
import com.typewars.service.gamesmanager.GamesManager;
import com.typewars.service.hunggames.HungStandardGamesService;
import org.springframework.stereotype.Service;

@Service
public class StandardGameService extends GameService {
    public StandardGameService(
            GamesManager gamesManager,
            FinishedStandardGamesService finishedStandardGamesService,
            HungStandardGamesService hungStandardGamesService) {
        super(gamesManager, finishedStandardGamesService, hungStandardGamesService);
    }

    @Override
    protected Game createGame(String gameId, String nickname) {
        return new StandardGame(gameId, nickname);
    }
}