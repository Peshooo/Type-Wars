package com.typewars.test;

import com.typewars.service.game.StandardGameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Duration;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CountDownLatch;

public class StandardGameTask implements Runnable {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static final int POLLS = 10000;

    private final StandardGameService standardGameService;
    private final CountDownLatch countDownLatch;
    private final List<String> fakeIdsToPoll;

    public StandardGameTask(StandardGameService standardGameService, CountDownLatch countDownLatch) {
        this.standardGameService = standardGameService;
        this.countDownLatch = countDownLatch;
        this.fakeIdsToPoll = new ArrayList<>();

        for (int i = 0; i < POLLS; i++) {
            fakeIdsToPoll.add(UUID.randomUUID().toString());
        }
    }

    @Override
    public void run() {
        long responseTimeSumMillis = 0;
        String gameId = standardGameService.createGame(UUID.randomUUID().toString());

        for (int i = 0; i < POLLS; i++) {
            responseTimeSumMillis += measurePolls(gameId, i);
        }

        logger.info("Average response time for {} polls: {} ms", POLLS, (double) responseTimeSumMillis / POLLS);

        countDownLatch.countDown();
    }

    private long measurePolls(String gameId, int fakePollIndex) {
        Instant instantFrom = Instant.now();
        standardGameService.getGameState(gameId);
        standardGameService.getGameState(fakeIdsToPoll.get(fakePollIndex));
        Instant instantTo = Instant.now();

        return Duration.between(instantFrom, instantTo).toMillis();
    }
}
