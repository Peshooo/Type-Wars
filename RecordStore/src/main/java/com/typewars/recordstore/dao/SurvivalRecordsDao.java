package com.typewars.recordstore.dao;

import com.typewars.recordstore.model.SurvivalRecord;
import org.springframework.context.annotation.DependsOn;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@EnableTransactionManagement
@DependsOn("flywayInitializer")
@EnableScheduling
public class SurvivalRecordsDao extends NamedParameterJdbcDaoSupport {
  private static final BeanPropertyRowMapper<SurvivalRecord> ROW_MAPPER = new BeanPropertyRowMapper<>(SurvivalRecord.class);

  public SurvivalRecordsDao(DataSource dataSource) {
    setDataSource(dataSource);
  }

  public void save(SurvivalRecord survivalRecord) {
    String sql = "INSERT INTO survival_records " +
        "(game_uuid, nickname, score, created_at) " +
        "VALUES (:game_uuid, :nickname, :score, :created_at)";
    Map<String, Object> params = new HashMap<>();
    params.put("game_uuid", survivalRecord.getGameUuid());
    params.put("nickname", survivalRecord.getNickname());
    params.put("score", survivalRecord.getScore());
    params.put("created_at", survivalRecord.getCreatedAt());

    getNamedParameterJdbcTemplate().update(sql, params);
  }

  public List<SurvivalRecord> getTopFiveLast24Hours() {
    String sql = "SELECT * FROM survival_records " +
        "WHERE created_at >= :before_24_hours " +
        "ORDER BY score DESC " +
        "LIMIT 5";
    Map<String, Object> params = new HashMap<>();
    params.put("before_24_hours", before24Hours());

    return getNamedParameterJdbcTemplate().query(sql, params, ROW_MAPPER);
  }

  @Scheduled(fixedRate = 1000 * 60 * 60 * 24)
  private void deleteOldRecords() {
    String sql = "DELETE FROM survival_records " +
        "WHERE created_at < :before_24_hours";

    Map<String, Object> params = new HashMap<>();
    params.put("before_24_hours", before24Hours());

    getNamedParameterJdbcTemplate().update(sql, params);
  }

  private OffsetDateTime before24Hours() {
    return OffsetDateTime.now().minusHours(24);
  }
}