package com.typewars.dao;

import com.google.common.collect.ImmutableMap;
import com.typewars.model.NotificationEntity;
import org.springframework.context.annotation.DependsOn;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.stereotype.Repository;

import javax.sql.DataSource;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@Repository
@DependsOn("flywayInitializer")
public class NotificationsDao extends NamedParameterJdbcDaoSupport {
    private static final BeanPropertyRowMapper<NotificationEntity> ROW_MAPPER = new BeanPropertyRowMapper<>(NotificationEntity.class);

    public NotificationsDao(DataSource dataSource) {
        setDataSource(dataSource);
    }

    public void save(NotificationEntity entity) {
        String sql = "INSERT INTO notifications " +
                "( message, created_at ) " +
                "VALUES ( :message, :created_at ) ";
        Map<String, Object> params = ImmutableMap.<String, Object>builder()
                .put("message", entity.getMessage())
                .put("created_at", entity.getCreatedAt())
                .build();

        getNamedParameterJdbcTemplate().update(sql, params);
    }

    public List<NotificationEntity> getAll() {
        String sql = "SELECT * FROM notifications ";

        try {
            return getNamedParameterJdbcTemplate().query(sql, ROW_MAPPER);
        } catch (EmptyResultDataAccessException e) {
            return Collections.emptyList();
        }
    }

    public void delete(List<Long> ids) {
        if (ids.isEmpty()) {
            return;
        }

        String sql = "DELETE FROM notifications " +
                "WHERE id IN ( :ids ) ";
        Map<String, Object> params = ImmutableMap.<String, Object>builder()
                .put("ids", ids)
                .build();

        getNamedParameterJdbcTemplate().update(sql, params);
    }
}
