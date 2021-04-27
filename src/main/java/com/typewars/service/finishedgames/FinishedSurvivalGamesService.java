package com.typewars.service.finishedgames;

import com.typewars.dao.SurvivalRecordsDao;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
public class FinishedSurvivalGamesService extends FinishedGamesService {
    public FinishedSurvivalGamesService(SurvivalRecordsDao survivalRecordsDao) {
        super(survivalRecordsDao);
    }
}