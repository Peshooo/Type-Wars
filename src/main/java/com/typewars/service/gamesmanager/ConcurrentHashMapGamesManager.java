package com.typewars.service.gamesmanager;

import com.typewars.service.Game;
import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

@Service
public class ConcurrentHashMapGamesManager implements GamesManager {
    private final ConcurrentHashMap<String, Game> games;

    public ConcurrentHashMapGamesManager() {
        games = new ConcurrentHashMap<>();
    }

    @Override
    public void save(Game game) {
        games.put(game.getMetadata().getId(), game);
    }

    @Override
    public Game get(String gameId) {
        return games.get(gameId);
    }

    @Override
    public void delete(String gameId) {
        games.remove(gameId);
    }

    @Override
    public void perform(String gameId, BiFunction<String, Game, Game> operation) {
        games.compute(gameId, operation);
    }
}
