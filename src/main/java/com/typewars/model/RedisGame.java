package com.typewars.model;

import java.io.Serializable;
import java.util.List;

public class RedisGame implements Serializable {
    private String id;
    private String nickname;
    private long createdAt;
    private GameStatus status;
    private long score;
    private long lastTimeMillis;
    private long timeLeftMillis;
    private List<Word> words;
    private String gameMode;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public long getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void setStatus(GameStatus status) {
        this.status = status;
    }

    public long getScore() {
        return score;
    }

    public void setScore(long score) {
        this.score = score;
    }

    public long getLastTimeMillis() {
        return lastTimeMillis;
    }

    public void setLastTimeMillis(long lastTimeMillis) {
        this.lastTimeMillis = lastTimeMillis;
    }

    public long getTimeLeftMillis() {
        return timeLeftMillis;
    }

    public void setTimeLeftMillis(long timeLeftMillis) {
        this.timeLeftMillis = timeLeftMillis;
    }

    public List<Word> getWords() {
        return words;
    }

    public void setWords(List<Word> words) {
        this.words = words;
    }

    public String getGameMode() {
        return gameMode;
    }

    public void setGameMode(String gameMode) {
        this.gameMode = gameMode;
    }
}
