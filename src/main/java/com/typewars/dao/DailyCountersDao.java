package com.typewars.dao;

import com.google.common.collect.ImmutableMap;
import com.typewars.model.DailyCounterEntity;
import org.springframework.context.annotation.DependsOn;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.List;
import java.util.Map;

@Repository
@DependsOn("flywayInitializer")
public class DailyCountersDao extends NamedParameterJdbcDaoSupport {
    private final BeanPropertyRowMapper<DailyCounterEntity> ROW_MAPPER = new BeanPropertyRowMapper<>(DailyCounterEntity.class);

    public DailyCountersDao(DataSource dataSource) {
        setDataSource(dataSource);
    }

    public void count(String key, Long day) {
        String sql = "INSERT INTO daily_counters " +
                "( key, count, day) " +
                "VALUES ( :key, :count, :day ) " +
                "ON CONFLICT ( key, day ) DO " +
                "UPDATE SET count = daily_counters.count + 1 ";
        Map<String, Object> params = ImmutableMap.<String, Object>builder()
                .put("key", key)
                .put("count", 1)
                .put("day", day)
                .build();

        getNamedParameterJdbcTemplate().update(sql, params);
    }

    public List<DailyCounterEntity> getAll() {
        String sql = "SELECT * FROM daily_counters ";

        return getNamedParameterJdbcTemplate().query(sql, ROW_MAPPER);
    }
}
