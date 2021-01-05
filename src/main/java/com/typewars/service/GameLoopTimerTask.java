package com.typewars.service;

import com.typewars.dao.RecordsDao;
import com.typewars.model.GameRecord;
import com.typewars.model.GameStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.OffsetDateTime;
import java.util.Timer;
import java.util.TimerTask;

public class GameLoopTimerTask extends TimerTask {
    private static final Logger logger = LoggerFactory.getLogger(GameLoopTimerTask.class);

    private static final long NOT_STARTED_EXPIRATION_TIME_MILLIS = 60000;
    private static final long DELETE_FINISHED_GAME_DELAY_MILLIS = 60000;
    private static final long DELETE_EXPIRED_GAME_DELAY_MILLIS = 0;

    private final RecordsDao recordsDao;
    private final GamesManager gamesManager;
    private final String gameMode;
    private final String gameId;

    public GameLoopTimerTask(String gameMode, String gameId, GamesManager gamesManager, RecordsDao recordsDao) {
        this.gamesManager = gamesManager;
        this.recordsDao = recordsDao;
        this.gameMode = gameMode;
        this.gameId = gameId;
    }

    @Override
    public void run() {
        try {
            gamesManager.perform(gameId, (id, game) -> {
                checkFinished(game);
                checkExpired(game);

                game.updateGame();

                return game;
            });
        } catch (Exception e) {
            logger.error("Exception in thread run ", e);
        }
    }

    private void checkFinished(Game game) {
        if (game.getStatus() == GameStatus.FINISHED) {
            afterGameFinished(game);
            cancel();
        }
    }

    private void checkExpired(Game game) {
        if (game.getStatus() == GameStatus.NOT_STARTED
                && System.currentTimeMillis() - game.getMetadata().getCreatedAt() > NOT_STARTED_EXPIRATION_TIME_MILLIS) {
            scheduleDelete(DELETE_EXPIRED_GAME_DELAY_MILLIS);
            cancel();
        }
    }

    private void afterGameFinished(Game game) {
        GameRecord gameRecord =
                new GameRecord(game.getMetadata().getId(), game.getMetadata().getNickname(), game.getScore(), OffsetDateTime.now());
        recordsDao.save(gameRecord);
        scheduleDelete(DELETE_FINISHED_GAME_DELAY_MILLIS);
    }

    private void scheduleDelete(long delay) {
        Timer deleteGameTimer = new Timer();
        deleteGameTimer.schedule(new DeleteGameTimerTask(gamesManager, gameId), delay);
    }
}