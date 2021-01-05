package com.typewars.dao;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;

@Repository
@DependsOn("flywayInitializer")
public class StandardRecordsDao extends RecordsDao {
    public StandardRecordsDao(
            @Value("${game.standard-mode.name}") String gameMode,
            DataSource dataSource) {
        super(gameMode, dataSource);
    }
}