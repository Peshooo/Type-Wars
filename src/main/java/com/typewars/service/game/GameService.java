package com.typewars.service.game;

import com.typewars.model.GameState;
import com.typewars.model.GameStatus;
import com.typewars.service.finishedgames.FinishedGamesService;
import com.typewars.service.gamesmanager.GamesManager;

import java.util.UUID;

public abstract class GameService {
    private final GamesManager gamesManager;
    private final FinishedGamesService finishedGamesService;

    public GameService(GamesManager gamesManager, FinishedGamesService finishedGamesService) {
        this.gamesManager = gamesManager;
        this.finishedGamesService = finishedGamesService;
    }

    public String createGame(String nickname) {
        String gameId = generateUuid();
        Game game = createGame(gameId, nickname);
        gamesManager.save(game);

        return gameId;
    }

    private String generateUuid() {
        return UUID.randomUUID().toString();
    }

    protected abstract Game createGame(String gameId, String nickname);

    public GameState getGameState(String gameId) {
        try {
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
