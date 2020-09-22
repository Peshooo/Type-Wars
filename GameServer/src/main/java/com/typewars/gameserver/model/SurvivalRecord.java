package com.typewars.gameserver.model;

import com.typewars.gameserver.common.PrintableJson;

import java.time.OffsetDateTime;

public class SurvivalRecord extends PrintableJson {
  private String gameUuid;
  private String nickname;
  private long score;
  private OffsetDateTime createdAt;

  public SurvivalRecord() {
  }

  public SurvivalRecord(String gameUuid, String nickname, long score, OffsetDateTime createdAt) {
    this.gameUuid = gameUuid;
    this.nickname = nickname;
    this.score = score;
    this.createdAt = createdAt;
  }

  public String getGameUuid() {
    return gameUuid;
  }

  public void setGameUuid(String gameUuid) {
    this.gameUuid = gameUuid;
  }

  public String getNickname() {
    return nickname;
  }

  public void setNickname(String nickname) {
    this.nickname = nickname;
  }

  public long getScore() {
    return score;
  }

  public void setScore(long score) {
    this.score = score;
  }

  public OffsetDateTime getCreatedAt() {
    return createdAt;
  }

  public void setCreatedAt(OffsetDateTime createdAt) {
    this.createdAt = createdAt;
  }
}