package com.typewars.gameserver.common;

public class CreateGameResponse extends PrintableJson {
  private String gameId;

  public CreateGameResponse() {
  }

  public CreateGameResponse(String gameId) {
    this.gameId = gameId;
  }

  public String getGameId() {
    return gameId;
  }

  public void setGameId(String gameId) {
    this.gameId = gameId;
  }
}