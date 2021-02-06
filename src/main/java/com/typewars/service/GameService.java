package com.typewars.service;

import com.typewars.dao.RecordsDao;
import com.typewars.model.GameRecord;
import com.typewars.model.GameState;
import com.typewars.model.GameStatus;
import com.typewars.service.gamesmanager.GamesManager;

import java.time.OffsetDateTime;
import java.util.Timer;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

public abstract class GameService {
    private static final long GAME_LOOP_PERIOD_MILLIS = 32;

    private final String gameMode;
    private final GamesManager gamesManager;
    private final RecordsDao recordsDao;

    public GameService(String gameMode, GamesManager gamesManager, RecordsDao recordsDao) {
        this.gameMode = gameMode;
        this.gamesManager = gamesManager;
        this.recordsDao = recordsDao;
    }

    public String createGame(String nickname) {
        String gameId = generateUuid();
        Game game = createGame(gameId, nickname);
        gamesManager.save(game);
        //startGameLoop(gameId);

        return gameId;
    }

    private String generateUuid() {
        return UUID.randomUUID().toString();
    }

    protected abstract Game createGame(String gameId, String nickname);

    public GameState getGameState(String gameId) {
        return gamesManager.perform(gameId, (id, game) -> updateGame(game)).getGameState();
    }

    private Game updateGame(Game game) {
        checkExpired(game);

        game.updateGame();

        checkFinished(game);

        return game;
    }

    private void checkFinished(Game game) {
        if (game.getStatus() == GameStatus.FINISHED) {
            afterGameFinished(game);
        }
    }

    private void checkExpired(Game game) {
        //TODO: Figure out delete of hung games
    }

    private void afterGameFinished(Game game) {
        GameRecord gameRecord =
                new GameRecord(game.getMetadata().getId(), game.getMetadata().getNickname(), game.getScore(), OffsetDateTime.now());
        recordsDao.save(gameRecord);
        gamesManager.delete(game.getMetadata().getId());
    }

    public void processEnteredWord(String gameId, String word) {
        CompletableFuture.runAsync(() -> gamesManager.perform(gameId, (id, game) -> enterWord(game, word)));
    }

    private Game enterWord(Game game, String word) {
        game.enterWord(word);

        return game;
    }
}
