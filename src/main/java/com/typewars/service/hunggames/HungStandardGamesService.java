package com.typewars.service.hunggames;

import com.typewars.service.finishedgames.FinishedStandardGamesService;
import com.typewars.service.gamesmanager.GamesManager;
import org.springframework.stereotype.Service;

@Service
public class HungStandardGamesService extends HungGamesService {
    public HungStandardGamesService(GamesManager gamesManager, FinishedStandardGamesService finishedStandardGamesService) {
        super(gamesManager, finishedStandardGamesService);
    }
}
