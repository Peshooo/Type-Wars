package com.typewars.service.game;

import com.typewars.model.GameState;
import com.typewars.model.GameStatus;
import com.typewars.service.finishedgames.FinishedGamesService;
import com.typewars.service.gamesmanager.GamesManager;
import com.typewars.service.hunggames.HungGamesService;

import java.util.UUID;

public abstract class GameService {
    private final GamesManager gamesManager;
    private final FinishedGamesService finishedGamesService;
    private final HungGamesService hungGamesService;

    public GameService(GamesManager gamesManager, FinishedGamesService finishedGamesService, HungGamesService hungGamesService) {
        this.gamesManager = gamesManager;
        this.finishedGamesService = finishedGamesService;
        this.hungGamesService = hungGamesService;
    }

    public String createGame(String nickname) {
        String gameId = generateUuid();
        Game game = createGame(gameId, nickname);
        hungGamesService.markAsRecentlyPolled(gameId);
        gamesManager.save(game);

        return gameId;
    }

    private String generateUuid() {
        return UUID.randomUUID().toString();
    }

    protected abstract Game createGame(String gameId, String nickname);

    public GameState getGameState(String gameId) {
        try {
            hungGamesService.markAsRecentlyPolled(gameId);
            return gamesManager.perform(gameId, this::updateGame).getGameState();
        } catch (Throwable e) {
            return null;
        }
    }

    private Game updateGame(String gameId, Game game) {
        game.updateGame();
        checkFinished(game);

        return game;
    }

    private void checkFinished(Game game) {
        if (game.getStatus() == GameStatus.FINISHED) {
            finishedGamesService.afterGameFinished(game);
        }
    }

    public void processEnteredWord(String gameId, String word) {
        gamesManager.perform(gameId, (id, game) -> enterWord(game, word));
    }

    private Game enterWord(Game game, String word) {
        game.enterWord(word);

        return game;
    }
}
