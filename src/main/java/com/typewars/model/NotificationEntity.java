package com.typewars.model;

import java.time.OffsetDateTime;

public class NotificationEntity {
    private Long id;
    private String message;
    private OffsetDateTime createdAt;

    public NotificationEntity() {
    }

    public NotificationEntity(String message, OffsetDateTime createdAt) {
        this.message = message;
        this.createdAt = createdAt;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public OffsetDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(OffsetDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
