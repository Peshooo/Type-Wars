package com.typewars.gameserver.service;

import com.typewars.gameserver.common.GameState;
import com.typewars.gameserver.common.GameStatus;
import com.typewars.gameserver.common.Word;
import com.typewars.gameserver.util.WordFactory;
import com.typewars.gameserver.util.WordProvider;

import java.util.ArrayList;
import java.util.Iterator;

public class GameLogic extends GameState {
  private static final long MAXIMUM_TIME_MILLIS = 10000;
  private static final long INITIAL_TIME_MILLIS = 10000;
  private static final long ADDITIONAL_MILLIS_PER_WORD = 1000;

  private static final int WORDS_ON_SCREEN = 10;

  private static final int CANVAS_WIDTH = 1200;
  private static final int CANVAS_HEIGHT = 600;

  private long lastTimeMillis;

  public GameLogic(String id, String nickname) {
    super(id, nickname);

    words = new ArrayList<>();
    score = 0;
    gameStatus = GameStatus.NOT_STARTED;
    timeLeftMillis = INITIAL_TIME_MILLIS;
    lastTimeMillis = System.currentTimeMillis();

    refillWords();
  }

  public synchronized void updateGame() {
    words.forEach(Word::move);
    words.removeIf(this::notOnCanvas);
    refillWords();
    updateTimeIfRunning();
  }

  private boolean notOnCanvas(Word word) {
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

  public synchronized void enterWord(String enteredWord) {
    if (this.gameStatus == GameStatus.FINISHED) {
      return;
    }

    if (this.gameStatus == GameStatus.NOT_STARTED) {
      this.gameStatus = GameStatus.RUNNING;
      this.lastTimeMillis = System.currentTimeMillis();
    }

    Iterator<Word> wordIterator = words.iterator();

    while (wordIterator.hasNext()) {
      String word = wordIterator.next().getWord();

      if (word.equals(enteredWord)) {
        this.timeLeftMillis += ADDITIONAL_MILLIS_PER_WORD;
        this.score += word.length();
        wordIterator.remove();
      }
    }

    this.timeLeftMillis = Math.min(this.timeLeftMillis, MAXIMUM_TIME_MILLIS);

    refillWords();
  }

  private void refillWords() {
    while (words.size() < WORDS_ON_SCREEN) {
      String word = WordProvider.getWord();
      words.add(WordFactory.create(word));
    }
  }
}