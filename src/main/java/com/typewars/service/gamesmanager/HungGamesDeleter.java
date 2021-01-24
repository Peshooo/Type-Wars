package com.typewars.service.gamesmanager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;

@Service
public class HungGamesDeleter {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final GamesManager gamesManager;

    private final Set<String> gamesNotStartedStage1;
    private final Set<String> gamesNotStartedStage2;
    private final Set<String> gamesNotStartedStage3;

    public HungGamesDeleter(GamesManager gamesManager) {
        this.gamesManager = gamesManager;

        gamesNotStartedStage1 = new ConcurrentSkipListSet<>();
        gamesNotStartedStage2 = new ConcurrentSkipListSet<>();
        gamesNotStartedStage3 = new ConcurrentSkipListSet<>();
    }

    public void gameCreated(String gameId) {
        gamesNotStartedStage1.add(gameId);
    }

    public void gameStarted(String gameId) {
        gamesNotStartedStage1.remove(gameId);
        gamesNotStartedStage2.remove(gameId);
        gamesNotStartedStage3.remove(gameId);
    }

    @Scheduled(fixedRate = 60000)
    private void advanceGamesNotStartedStage()
    {
        logger.info("Delete hung games loop.");

        gamesNotStartedStage3.forEach(gameId -> {
            logger.info("Deleting hung game {}.", gameId);
            gamesManager.delete(gameId);
        });

        gamesNotStartedStage3.clear();
        gamesNotStartedStage3.addAll(gamesNotStartedStage2);

        gamesNotStartedStage2.clear();
        gamesNotStartedStage2.addAll(gamesNotStartedStage1);

        gamesNotStartedStage1.clear();
    }
}
