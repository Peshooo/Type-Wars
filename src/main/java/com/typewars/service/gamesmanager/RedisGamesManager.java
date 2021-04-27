package com.typewars.service.gamesmanager;

import com.typewars.model.RedisGame;
import com.typewars.model.SerializableDummyObject;
import com.typewars.service.game.Game;
import com.typewars.service.game.StandardGame;
import com.typewars.service.game.SurvivalGame;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;
import java.util.function.BiFunction;

@Service
public class RedisGamesManager implements GamesManager {
    public static final String ACTIVE_GAMES_HASH_NAME_PREFIX = "ag.";
    private static final String ACTIVE_GAMES_HASH_NAME_FORMAT = ACTIVE_GAMES_HASH_NAME_PREFIX + "%s";

    private static final String GAMES_HASH_NAME = "games";

    private static final int EXPIRATION_SECONDS = 63;

    private final RedisTemplate<String, Object> redisTemplate;

    public RedisGamesManager(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void save(Game game) {
        HashOperations<String, String, RedisGame> hashOperations = redisTemplate.opsForHash();
        RedisGame redisGame = game.toRedisGame();
        hashOperations.put(GAMES_HASH_NAME, redisGame.getId(), redisGame);

        String valueOperationsName = String.format(ACTIVE_GAMES_HASH_NAME_FORMAT, redisGame.getId());
        BoundValueOperations<String, Object> valueOperations = redisTemplate.boundValueOps(valueOperationsName);
        valueOperations.set(SerializableDummyObject.getInstance());
        valueOperations.expire(EXPIRATION_SECONDS, TimeUnit.SECONDS);
    }

    @Override
    public Game get(String gameId) {
        HashOperations<String, String, RedisGame> hashOperations = redisTemplate.opsForHash();
        RedisGame redisGame = hashOperations.get(GAMES_HASH_NAME, gameId);

        return redisGame.getGameMode().equals("survival") ? new SurvivalGame(redisGame) : new StandardGame(redisGame);
    }

    @Override
    public void delete(String gameId) {
        HashOperations<String, String, RedisGame> hashOperations = redisTemplate.opsForHash();

        hashOperations.delete(GAMES_HASH_NAME, gameId);
    }

    @Override
    public Game perform(String gameId, BiFunction<String, Game, Game> operation) {
        HashOperations<String, String, RedisGame> hashOperations = redisTemplate.opsForHash();
        RedisGame redisGame = hashOperations.get(GAMES_HASH_NAME, gameId);

        if (redisGame != null) {
            Game game = redisGame.getGameMode().equals("survival") ? new SurvivalGame(redisGame) : new StandardGame(redisGame);
            game = operation.apply(gameId, game);
            hashOperations.put(GAMES_HASH_NAME, redisGame.getId(), game.toRedisGame());

            String valueOperationsName = String.format(ACTIVE_GAMES_HASH_NAME_FORMAT, redisGame.getId());
            BoundValueOperations<String, Object> valueOperations = redisTemplate.boundValueOps(valueOperationsName);
            valueOperations.expire(EXPIRATION_SECONDS, TimeUnit.SECONDS);

            return game;
        }

        return null;
    }
}
