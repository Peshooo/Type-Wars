package com.typewars.service;

import com.typewars.service.gamesmanager.GamesManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.TimerTask;

public class DeleteGameTimerTask extends TimerTask {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final GamesManager gamesManager;
    private final String gameId;

    public DeleteGameTimerTask(GamesManager gamesManager, String gameId) {
        this.gamesManager = gamesManager;
        this.gameId = gameId;
    }

    @Override
    public void run() {
        try {
            gamesManager.delete(gameId);
        } catch (Exception e) {
            logger.error("Exception in thread run ", e);
        }
    }
}
