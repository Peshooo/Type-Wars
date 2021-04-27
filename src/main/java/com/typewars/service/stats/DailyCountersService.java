package com.typewars.service.stats;

import com.typewars.dao.DailyCountersDao;
import com.typewars.model.DailyCounterEntity;
import org.springframework.stereotype.Service;

@Service
public class DailyCountersService {
    private final DailyCountersDao dailyCountersDao;

    public DailyCountersService(DailyCountersDao dailyCountersDao) {
        this.dailyCountersDao = dailyCountersDao;
    }

    public void count(String key) {
        Long day = getCurrentDay();
        dailyCountersDao.count(key, day);
    }

    private Long getCurrentDay() {
        return System.currentTimeMillis() / (1000L * 60 * 60 * 24);
    }
}
