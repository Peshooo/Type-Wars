package com.typewars.service;

import com.typewars.dao.RecordsDao;
import com.typewars.model.GameState;
import com.typewars.service.gamesmanager.GamesManager;

import java.util.Timer;
import java.util.UUID;

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
        startGameLoop(gameId);

        return gameId;
    }

    private String generateUuid() {
        return UUID.randomUUID().toString();
    }

    protected abstract Game createGame(String gameId, String nickname);

    private void startGameLoop(String gameId) {
        Timer gameLoopTimer = new Timer();
        gameLoopTimer.scheduleAtFixedRate(new GameLoopTimerTask(gameMode, gameId, gamesManager, recordsDao), 0, GAME_LOOP_PERIOD_MILLIS);
    }

    public GameState getGameState(String gameId) {
        return gamesManager.get(gameId).getGameState();
    }

    public void processEnteredWord(String gameId, String word) {
        gamesManager.perform(gameId, (id, game) -> enterWord(game, word));
    }

    private Game enterWord(Game game, String word) {
        System.out.println("Performing enter word for game " + game.getMetadata().getId() + " and word " + word);
        game.enterWord(word);
        System.out.println("Words are " + game.getWords().size());

        return game;
    }
}
