package com.typewars.service;

import com.typewars.dao.RecordsDao;
import com.typewars.model.GameRecord;
import com.typewars.model.GameState;
import com.typewars.model.GameStatus;
import com.typewars.service.gamesmanager.GamesManager;
import com.typewars.service.gamesmanager.HungGamesDeleter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;

import java.time.OffsetDateTime;
import java.util.Set;
import java.util.Timer;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentSkipListSet;

public abstract class GameService {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final String gameMode;
    private final GamesManager gamesManager;
    private final RecordsDao recordsDao;
    private final HungGamesDeleter hungGamesDeleter;

    public GameService(String gameMode, GamesManager gamesManager, RecordsDao recordsDao, HungGamesDeleter hungGamesDeleter) {
        this.gameMode = gameMode;
        this.gamesManager = gamesManager;
        this.recordsDao = recordsDao;
        this.hungGamesDeleter = hungGamesDeleter;
    }

    public String createGame(String nickname) {
        String gameId = generateUuid();
        Game game = createGame(gameId, nickname);
        gamesManager.save(game);
        hungGamesDeleter.gameCreated(gameId);

        return gameId;
    }

    private String generateUuid() {
        return UUID.randomUUID().toString();
    }

    protected abstract Game createGame(String gameId, String nickname);

    private void afterGameFinished(Game game) {
        GameRecord gameRecord =
                new GameRecord(game.getMetadata().getId(), game.getMetadata().getNickname(), game.getScore(), OffsetDateTime.now());
        recordsDao.save(gameRecord);
        //scheduleDelete(DELETE_FINISHED_GAME_DELAY_MILLIS);
    }

//    private void scheduleDelete(long delay) {
//        Timer deleteGameTimer = new Timer();
//        deleteGameTimer.schedule(new DeleteGameTimerTask(gamesManager, gameId), delay);
//    }
    /*END*/

    public GameState getGameState(String gameId) {
        GameState gameState = gamesManager.perform(gameId, (id, game) -> game.updateGame()).getGameState();

        return gameState;
    }

    public void processEnteredWord(String gameId, String word) {
            gamesManager.perform(gameId, (id, game) -> enterWord(game, word));
            hungGamesDeleter.gameStarted(gameId);
    }

    private Game enterWord(Game game, String word) {
        System.out.println("Performing enter word for game " + game.getMetadata().getId() + " and word " + word);
        game.enterWord(word);
        System.out.println("Words are " + game.getWords().size());

        return game;
    }
}
