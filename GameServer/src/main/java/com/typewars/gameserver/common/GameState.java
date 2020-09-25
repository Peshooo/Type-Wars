package com.typewars.gameserver.common;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.List;

public class GameState extends PrintableJson {
  @JsonIgnore
  protected String id;

  @JsonIgnore
  protected long createdAt;

  protected String nickname;

  protected long score;
  protected GameStatus gameStatus;
  protected long timeLeftMillis;
  protected List<Word> words;

  public GameState() {
  }

  public GameState(String id, String nickname) {
    this.id = id;
    this.nickname = nickname;
  }

  public synchronized String getId() {
    return id;
  }

  public void setId(String id) {
    this.id = id;
  }

  public long getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(long createdAt) {
    this.createdAt = createdAt;
  }

  public synchronized long getScore() {
    return score;
  }

  public void setScore(long score) {
    this.score = score;
  }

  public synchronized GameStatus getGameStatus() {
    return gameStatus;
  }

  public void setGameStatus(GameStatus gameStatus) {
    this.gameStatus = gameStatus;
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

  public synchronized String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }
}