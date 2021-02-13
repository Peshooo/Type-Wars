package com.typewars.service.gamesmanager;

import com.typewars.model.RedisGame;
import com.typewars.service.game.Game;
import com.typewars.service.game.StandardGame;
import com.typewars.service.game.SurvivalGame;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.util.function.BiFunction;

@Service
@Profile("redis")
public class RedisGamesManager implements GamesManager {

    private static final String HASH_NAME = "games";

    private final RedisTemplate<String, RedisGame> redisTemplate;

    public RedisGamesManager(RedisTemplate<String, RedisGame> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Override
    public void save(Game game) {
        HashOperations<String, String, RedisGame> hashOperations = redisTemplate.opsForHash();
        RedisGame redisGame = game.toRedisGame();
        hashOperations.put(HASH_NAME, redisGame.getId(), redisGame);
    }

    @Override
    public Game get(String gameId) {
        HashOperations<String, String, RedisGame> hashOperations = redisTemplate.opsForHash();
        RedisGame redisGame = hashOperations.get(HASH_NAME, gameId);

        return redisGame.getGameMode().equals("survival") ? new SurvivalGame(redisGame) : new StandardGame(redisGame);
    }

    @Override
    public void delete(String gameId) {
        HashOperations<String, String, RedisGame> hashOperations = redisTemplate.opsForHash();

        hashOperations.delete(HASH_NAME, gameId);
    }

    @Override
    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Game perform(String gameId, BiFunction<String, Game, Game> operation) {
        HashOperations<String, String, RedisGame> hashOperations = redisTemplate.opsForHash();
        RedisGame redisGame = hashOperations.get(HASH_NAME, gameId);

        if (redisGame != null) {
            Game game = redisGame.getGameMode().equals("survival") ? new SurvivalGame(redisGame) : new StandardGame(redisGame);
            game = operation.apply(gameId, game);
            hashOperations.put(HASH_NAME, redisGame.getId(), game.toRedisGame());

            return game;
        }

        return null;
    }
}
