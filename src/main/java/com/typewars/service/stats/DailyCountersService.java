package com.typewars.service.stats;

import com.typewars.dao.DailyCountersDao;
import org.springframework.stereotype.Service;

@Service
public class DailyCountersService {
    private final DailyCountersDao dailyCountersDao;

    private static final long MILLISECONDS_IN_A_DAY = 1000L * 60 * 60 * 24;

    public DailyCountersService(DailyCountersDao dailyCountersDao) {
        this.dailyCountersDao = dailyCountersDao;
    }

    public void count(String key) {
        Long day = getCurrentDay();
        dailyCountersDao.count(key, day);
    }

    private Long getCurrentDay() {
        return System.currentTimeMillis() / MILLISECONDS_IN_A_DAY;
    }
}
