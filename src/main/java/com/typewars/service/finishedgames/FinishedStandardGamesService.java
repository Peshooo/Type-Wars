package com.typewars.service.finishedgames;

import com.typewars.dao.StandardRecordsDao;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

@Service
public class FinishedStandardGamesService extends FinishedGamesService {
    public FinishedStandardGamesService(StandardRecordsDao standardRecordsDao) {
        super(standardRecordsDao);
    }
}
