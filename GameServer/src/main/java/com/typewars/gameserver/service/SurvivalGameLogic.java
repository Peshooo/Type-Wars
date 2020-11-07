package com.typewars.gameserver.service;

import com.typewars.gameserver.common.Word;

import java.util.Iterator;

public class SurvivalGameLogic extends GameLogic {
  private static final long MAXIMUM_TIME_MILLIS = 10000;
  private static final long INITIAL_TIME_MILLIS = 10000;
  private static final long ADDITIONAL_MILLIS_PER_WORD = 1000;

  public SurvivalGameLogic(String id, String nickname) {
    super(id, nickname);
    timeLeftMillis = INITIAL_TIME_MILLIS;
  }

  @Override
  protected void processEnteredWord(String enteredWord) {
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
  }
}