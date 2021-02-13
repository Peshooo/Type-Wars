package com.typewars.test;

import com.typewars.dao.RecordsDao;
import com.typewars.model.GameRecord;
import com.typewars.service.finishedgames.FinishedGamesService;
import com.typewars.service.finishedgames.FinishedStandardGamesService;
import com.typewars.service.game.Game;
import com.typewars.service.game.GameService;
import com.typewars.service.game.StandardGameService;
import com.typewars.service.gamesmanager.ConcurrentHashMapGamesManager;
import com.typewars.service.gamesmanager.GamesManager;
import com.typewars.service.hunggames.HungGamesService;
import com.typewars.service.hunggames.HungStandardGamesService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Collections;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@RunWith(SpringJUnit4ClassRunner.class)
public class LoadTest {
    private static final int GAMES_STORED = 1000000;
    private static final int THREAD_POOL_SIZE = 40;

    @Test
    public void loadTestStandardGameService() throws InterruptedException {
        StandardGameService standardGameService = buildStandardGameService();
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

    private StandardGameService buildStandardGameService() {
        FinishedStandardGamesService finishedStandardGamesService = buildFinishedStandardGamesServiceMock();
        GamesManager gamesManager = new ConcurrentHashMapGamesManager();
        HungStandardGamesService hungStandardGamesService = new HungStandardGamesService(gamesManager, finishedStandardGamesService);

        return new StandardGameService(gamesManager, finishedStandardGamesService, hungStandardGamesService);
    }

    private FinishedStandardGamesService buildFinishedStandardGamesServiceMock() {
        FinishedStandardGamesService finishedStandardGamesService = Mockito.mock(FinishedStandardGamesService.class);
        Mockito.doNothing()
                .when(finishedStandardGamesService)
                .afterGameFinished(Mockito.any(Game.class));

        return finishedStandardGamesService;
    }
}
