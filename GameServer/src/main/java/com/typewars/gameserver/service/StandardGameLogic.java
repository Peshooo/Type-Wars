package com.typewars.gameserver.service;

import com.typewars.gameserver.common.Word;

import java.util.Iterator;

public class StandardGameLogic extends GameLogic {
  private static final long INITIAL_TIME_MILLIS = 60000;

  public StandardGameLogic(String id, String nickname) {
    super(id, nickname);
    timeLeftMillis = INITIAL_TIME_MILLIS;
  }

  @Override
  protected void processEnteredWord(String enteredWord) {
    Iterator<Word> wordIterator = words.iterator();

    while (wordIterator.hasNext()) {
      String word = wordIterator.next().getWord();

      if (word.equals(enteredWord)) {
        this.score += word.length();
        wordIterator.remove();
      }
    }
  }
}