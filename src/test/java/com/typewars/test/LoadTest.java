package com.typewars.test;

import com.typewars.model.RedisGame;
import com.typewars.model.SerializableDummyObject;
import com.typewars.service.finishedgames.FinishedStandardGamesService;
import com.typewars.service.game.Game;
import com.typewars.service.game.StandardGameService;
import com.typewars.service.gamesmanager.GamesManager;
import com.typewars.service.gamesmanager.RedisGamesManager;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.*;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RunWith(SpringJUnit4ClassRunner.class)
public class LoadTest {
    private static final int GAMES_STORED = 100;
    private static final int THREAD_POOL_SIZE = 8;

    @Test
    public void loadTestStandardGameServiceRedisGamesManager() throws InterruptedException {
        FinishedStandardGamesService finishedStandardGamesService = buildFinishedStandardGamesServiceMock();
        LettuceConnectionFactory lettuceConnectionFactory = new LettuceConnectionFactory("localhost", 6379);
        lettuceConnectionFactory.afterPropertiesSet();
        RedisTemplate<String, Object> redisTemplate = new RedisTemplate<>();
        redisTemplate.setConnectionFactory(lettuceConnectionFactory);
        redisTemplate.setKeySerializer(new StringRedisSerializer());
        redisTemplate.setHashKeySerializer(new StringRedisSerializer());
        redisTemplate.setValueSerializer(new Jackson2JsonRedisSerializer<>(SerializableDummyObject.class));
        redisTemplate.setHashValueSerializer(new Jackson2JsonRedisSerializer<>(RedisGame.class));
        redisTemplate.afterPropertiesSet();
        GamesManager gamesManager = new RedisGamesManager(redisTemplate);
        StandardGameService standardGameService = new StandardGameService(gamesManager, finishedStandardGamesService);

        ExecutorService executorService = Executors.newFixedThreadPool(THREAD_POOL_SIZE);
        CountDownLatch countDownLatch = new CountDownLatch(THREAD_POOL_SIZE);

        createGames(standardGameService);
        submitPollingTasks(standardGameService, executorService, countDownLatch);

        countDownLatch.await();
    }

    private void createGames(StandardGameService standardGameService) {
        for (int i = 1; i <= GAMES_STORED; i++) {
            standardGameService.createGame(UUID.randomUUID().toString());
        }
    }

    private void submitPollingTasks(StandardGameService standardGameService, ExecutorService executorService, CountDownLatch countDownLatch) {
        for (int i = 1; i <= THREAD_POOL_SIZE; i++) {
            executorService.submit(new StandardGameTask(standardGameService, countDownLatch));
        }
    }

    private FinishedStandardGamesService buildFinishedStandardGamesServiceMock() {
        FinishedStandardGamesService finishedStandardGamesService = Mockito.mock(FinishedStandardGamesService.class);
        Mockito.doNothing()
                .when(finishedStandardGamesService)
                .afterGameFinished(Mockito.any(Game.class));

        return finishedStandardGamesService;
    }
}
