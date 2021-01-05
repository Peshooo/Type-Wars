package com.typewars.service;

import com.typewars.model.Word;

import java.util.Iterator;

public class SurvivalGame extends Game {
    private static final long INITIAL_TIME_MILLIS = 10000;

    private static final long MAXIMUM_TIME_MILLIS = 10000;
    private static final long ADDITIONAL_MILLIS_PER_WORD = 1000;

    public SurvivalGame(String id, String nickname) {
        super(id, nickname);
    }

    @Override
    protected void processEnteredWord(String enteredWord) {
        Iterator<Word> wordIterator = words.iterator();

        while (wordIterator.hasNext()) {
            String word = wordIterator.next().getWord();

            if (word.equals(enteredWord)) {
                timeLeftMillis += ADDITIONAL_MILLIS_PER_WORD;
                score += word.length();
                wordIterator.remove();
            }
        }

        timeLeftMillis = Math.min(timeLeftMillis, MAXIMUM_TIME_MILLIS);
    }

    @Override
    protected long getInitialTimeMillis() {
        return INITIAL_TIME_MILLIS;
    }
}
