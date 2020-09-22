package com.typewars.web.gameserver;

import java.util.List;

public class GameState {
  private String nickname;

  private long score;
  private GameStatus gameStatus;
  private long timeLeftMillis;
  private List<Word> words;

  public GameState() {
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

  public GameStatus getGameStatus() {
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
}
