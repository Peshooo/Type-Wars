package com.typewars.service;

import com.typewars.dao.StandardRecordsDao;
import com.typewars.service.gamesmanager.GamesManager;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class StandardGameService extends GameService {
    public StandardGameService(
            @Value("${game.standard-mode.name}") String gameMode,
            GamesManager gamesManager,
            StandardRecordsDao standardRecordsDao) {
        super(gameMode, gamesManager, standardRecordsDao);
    }

    @Override
    protected Game createGame(String gameId, String nickname) {
        return new StandardGame(gameId, nickname);
    }
}