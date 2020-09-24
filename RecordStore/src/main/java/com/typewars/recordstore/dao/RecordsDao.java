package com.typewars.recordstore.dao;

import com.typewars.recordstore.model.GameRecord;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.scheduling.annotation.Scheduled;

import javax.sql.DataSource;
import java.time.OffsetDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RecordsDao extends NamedParameterJdbcDaoSupport {
  private static final BeanPropertyRowMapper<GameRecord> ROW_MAPPER = new BeanPropertyRowMapper<>(GameRecord.class);

  private final String gameMode;

  public RecordsDao(String gameMode, DataSource dataSource) {
    this.gameMode = gameMode;
    setDataSource(dataSource);
  }

  public void save(GameRecord gameRecord) {
    String sql = String.format("INSERT INTO %s_records ", gameMode) +
        "(game_uuid, nickname, score, created_at) " +
        "VALUES (:game_uuid, :nickname, :score, :created_at)";
    Map<String, Object> params = new HashMap<>();
    params.put("game_uuid", gameRecord.getGameUuid());
    params.put("nickname", gameRecord.getNickname());
    params.put("score", gameRecord.getScore());
    params.put("created_at", gameRecord.getCreatedAt());

    getNamedParameterJdbcTemplate().update(sql, params);
  }

  public List<GameRecord> getTopFiveLast24Hours() {
    String sql = String.format("SELECT * FROM %s_records ", gameMode) +
        "WHERE created_at >= :before_24_hours " +
        "ORDER BY score DESC " +
        "LIMIT 5";
    Map<String, Object> params = new HashMap<>();
    params.put("before_24_hours", before24Hours());

    return getNamedParameterJdbcTemplate().query(sql, params, ROW_MAPPER);
  }

  @Scheduled(fixedRate = 1000 * 60 * 60 * 24)
  private void deleteOldRecords() {
    String sql = String.format("DELETE FROM %s_records ", gameMode) +
        "WHERE created_at < :before_24_hours";

    Map<String, Object> params = new HashMap<>();
    params.put("before_24_hours", before24Hours());

    getNamedParameterJdbcTemplate().update(sql, params);
  }

  private OffsetDateTime before24Hours() {
    return OffsetDateTime.now().minusHours(24);
  }
}