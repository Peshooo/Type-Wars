package com.typewars.service.hunggames;

import com.typewars.model.GameStatus;
import com.typewars.service.finishedgames.FinishedGamesService;
import com.typewars.service.game.Game;
import com.typewars.service.gamesmanager.GamesManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

import javax.annotation.PreDestroy;
import java.util.HashSet;
import java.util.Set;

public class HungGamesService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final GamesManager gamesManager;
    private final FinishedGamesService finishedGamesService;

    private final Set<String> s1;
    private final Set<String> s2;
    private final Set<String> s3;

    public HungGamesService(GamesManager gamesManager, FinishedGamesService finishedGamesService) {
        this.gamesManager = gamesManager;
        this.finishedGamesService = finishedGamesService;
        s1 = new HashSet<>();
        s2 = new HashSet<>();
        s3 = new HashSet<>();
    }

    public void markAsRecentlyPolled(String gameId) {
        s3.add(gameId);
    }

    @PreDestroy
    private void onShutdown() {
        advance();
    }

    @Scheduled(fixedRate = 60000)
    private void advance() {
        logger.info("Hung games loop");

        s1.forEach(this::removeGameIfHung);
        s1.clear();
        s1.addAll(s2);
        s2.clear();
        s2.addAll(s3);
        s3.clear();
    }

    private void removeGameIfHung(String gameId) {
        if (isGameHung(gameId)) {
            Game game = gamesManager.get(gameId);

            if (game == null) {
                return;
            }

            GameStatus hungStatus = game.getStatus();

            if (hungStatus == GameStatus.FINISHED) {
                return;
            }

            gamesManager.perform(gameId, this::updateGame);

            if (game.getStatus() == GameStatus.FINISHED) {
                logger.info("Deleting finished hung game {}", gameId);
                finishedGamesService.afterGameFinished(game);
            } else {
                logger.info("Deleting hung game {}", gameId);
                gamesManager.delete(gameId);
            }
        }
    }

    private Game updateGame(String gameId, Game game) {
        game.updateGame();

        return game;
    }

    private boolean isGameHung(String gameId) {
        return !s2.contains(gameId) && !s3.contains(gameId);
    }
}
