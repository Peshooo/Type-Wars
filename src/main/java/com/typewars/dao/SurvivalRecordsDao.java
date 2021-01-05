package com.typewars.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
@DependsOn("flywayInitializer")
public class SurvivalRecordsDao extends RecordsDao {
    public SurvivalRecordsDao(
            @Value("${game.survival-mode.name}") String gameMode,
            DataSource dataSource) {
        super(gameMode, dataSource);
    }
}