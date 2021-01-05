package com.typewars.service;

import com.typewars.model.Word;

import java.util.Iterator;

public class StandardGame extends Game {
    private static final long INITIAL_TIME_MILLIS = 60000;

    public StandardGame(String id, String nickname) {
        super(id, nickname);
    }

    @Override
    protected void processEnteredWord(String enteredWord) {
        Iterator<Word> wordIterator = words.iterator();

        while (wordIterator.hasNext()) {
            String word = wordIterator.next().getWord();

            if (word.equals(enteredWord)) {
                score += word.length();
                wordIterator.remove();
            }
        }
    }

    @Override
    protected long getInitialTimeMillis() {
        return INITIAL_TIME_MILLIS;
    }
}