package com.typewars.model;

public class GameMetadata {
    private String id;
    private String nickname;
    private long createdAt;

    public GameMetadata(String id, String nickname, long createdAt) {
        this.id = id;
        this.nickname = nickname;
        this.createdAt = createdAt;
    }

    public String getId() {
        return id;
    }

    public String getNickname() {
        return nickname;
    }

    public long getCreatedAt() {
        return createdAt;
    }
}
