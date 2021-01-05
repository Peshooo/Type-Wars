package com.typewars.service;

import org.springframework.stereotype.Service;

import java.util.concurrent.ConcurrentHashMap;
import java.util.function.BiFunction;

@Service
public class GamesManager {
    private final ConcurrentHashMap<String, Game> games;

    public GamesManager() {
        games = new ConcurrentHashMap<>();
    }

    public void save(Game game) {
        games.put(game.getMetadata().getId(), game);
    }

    public Game get(String gameId) {
        return games.get(gameId);
    }

    public void delete(String gameId) {
        games.remove(gameId);
    }

    public void perform(String gameId, BiFunction<String, Game, Game> operation) {
        games.compute(gameId, operation);
    }
}
