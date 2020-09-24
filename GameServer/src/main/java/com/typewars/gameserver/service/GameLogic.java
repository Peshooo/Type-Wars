package com.typewars.gameserver.service;

import com.typewars.gameserver.common.GameState;
import com.typewars.gameserver.common.GameStatus;
import com.typewars.gameserver.common.Word;
import com.typewars.gameserver.util.WordFactory;
import com.typewars.gameserver.util.WordProvider;

import java.util.ArrayList;

public abstract class GameLogic extends GameState {
  protected static final int WORDS_ON_SCREEN = 10;
  protected static final int CANVAS_WIDTH = 1200;
  protected static final int CANVAS_HEIGHT = 600;

  protected long lastTimeMillis;

  public GameLogic(String id, String nickname) {
    super(id, nickname);

    words = new ArrayList<>();
    score = 0;
    gameStatus = GameStatus.NOT_STARTED;
    lastTimeMillis = System.currentTimeMillis();

    refillWords();
  }

  public synchronized void enterWord(String enteredWord) {
    if (this.gameStatus == GameStatus.FINISHED) {
      return;
    }

    if (this.gameStatus == GameStatus.NOT_STARTED) {
      this.gameStatus = GameStatus.RUNNING;
      this.lastTimeMillis = System.currentTimeMillis();
    }

    processEnteredWord(enteredWord);

    refillWords();
  }

  protected abstract void processEnteredWord(String enteredWord);

  public synchronized void updateGame() {
    words.forEach(Word::move);
    words.removeIf(this::notOnCanvas);
    refillWords();
    updateTimeIfRunning();
  }

  protected boolean notOnCanvas(Word word) {
    if (word.getPosition().getX() < 0 || word.getPosition().getY() < 0) {
      return true;
    }

    return word.getPosition().getX() + word.getSize().getWidth() > CANVAS_WIDTH
        || word.getPosition().getY() + word.getSize().getHeight() > CANVAS_HEIGHT;
  }

  private void updateTimeIfRunning() {
    if (this.gameStatus == GameStatus.RUNNING) {
      updateTime();
    }
  }

  private void updateTime() {
    long currentTimeMillis = System.currentTimeMillis();
    this.timeLeftMillis -= (currentTimeMillis - lastTimeMillis);
    this.lastTimeMillis = currentTimeMillis;

    finishGameIfNoTimeLeft();
  }

  private void finishGameIfNoTimeLeft() {
    if (this.timeLeftMillis <= 0) {
      this.timeLeftMillis = 0;
      this.gameStatus = GameStatus.FINISHED;
    }
  }

  private void refillWords() {
    while (words.size() < WORDS_ON_SCREEN) {
      String word = WordProvider.getWord();
      words.add(WordFactory.create(word));
    }
  }
}