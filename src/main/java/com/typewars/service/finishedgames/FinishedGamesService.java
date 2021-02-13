package com.typewars.service.finishedgames;

import com.typewars.dao.RecordsDao;
import com.typewars.model.GameRecord;
import com.typewars.service.game.Game;

import java.time.OffsetDateTime;

public abstract class FinishedGamesService {
    private final RecordsDao recordsDao;

    public FinishedGamesService(RecordsDao recordsDao) {
        this.recordsDao = recordsDao;
    }

    public void afterGameFinished(Game game) {
        GameRecord gameRecord =
                new GameRecord(game.getMetadata().getId(), game.getMetadata().getNickname(), game.getScore(), OffsetDateTime.now());
        recordsDao.save(gameRecord);
    }
}
