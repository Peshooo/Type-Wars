package com.typewars.recordstore.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

@Component
@EnableTransactionManagement
@DependsOn("flywayInitializer")
@EnableScheduling
public class SurvivalRecordsDao extends RecordsDao {
  public SurvivalRecordsDao(
      @Value("${game.survival-mode.name}") String gameMode,
      DataSource dataSource) {
    super(gameMode, dataSource);
  }
}