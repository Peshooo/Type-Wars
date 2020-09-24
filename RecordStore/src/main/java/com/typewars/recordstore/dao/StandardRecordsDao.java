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
public class StandardRecordsDao extends RecordsDao {
  public StandardRecordsDao(
      @Value("${game.standard-mode.name}") String gameMode,
      DataSource dataSource) {
    super(gameMode, dataSource);
  }
}
