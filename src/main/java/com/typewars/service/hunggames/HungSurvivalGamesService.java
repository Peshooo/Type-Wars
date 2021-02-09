package com.typewars.service.hunggames;

import com.typewars.service.finishedgames.FinishedSurvivalGamesService;
import com.typewars.service.gamesmanager.GamesManager;
import org.springframework.stereotype.Service;

@Service
public class HungSurvivalGamesService extends HungGamesService {
    public HungSurvivalGamesService(GamesManager gamesManager, FinishedSurvivalGamesService finishedSurvivalGamesService) {
        super(gamesManager, finishedSurvivalGamesService);
    }
}
