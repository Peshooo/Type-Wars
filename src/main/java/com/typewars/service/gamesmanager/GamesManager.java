package com.typewars.service.gamesmanager;

import com.typewars.service.Game;

import java.util.function.BiFunction;

public interface GamesManager {
    void save(Game game);

    Game get(String gameId);

    void delete(String gameId);

    void perform(String gameId, BiFunction<String, Game, Game> operation);
}
