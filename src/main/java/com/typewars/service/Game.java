package com.typewars.service;

import com.typewars.model.GameMetadata;
import com.typewars.model.GameState;
import com.typewars.model.GameStatus;
import com.typewars.model.Word;

import java.util.ArrayList;
import java.util.List;

public abstract class Game {
    protected static final int ACTIVE_WORDS = 10;
    protected static final int CANVAS_WIDTH = 1200;
    protected static final int CANVAS_HEIGHT = 600;

    private GameMetadata metadata;
    private GameStatus status;
    protected long score;
    private long lastTimeMillis;
    protected long timeLeftMillis;
    protected List<Word> words;

    public Game(String id, String nickname) {
        long createdAt = System.currentTimeMillis();
        metadata = new GameMetadata(id, nickname, createdAt);
        status = GameStatus.NOT_STARTED;
        score = 0;
        lastTimeMillis = createdAt;
        timeLeftMillis = getInitialTimeMillis();
        words = new ArrayList<>();
    }

    public GameState getGameState() {
        GameState gameState = new GameState();
        gameState.setNickname(metadata.getNickname());
        gameState.setScore(score);
        gameState.setStatus(status);
        gameState.setTimeLeftMillis(timeLeftMillis);
        gameState.setWords(words);

        return gameState;
    }

    public long getScore() {
        return score;
    }

    public GameMetadata getMetadata() {
        return metadata;
    }

    public GameStatus getStatus() {
        return status;
    }

    public void enterWord(String enteredWord) {
        if (status == GameStatus.FINISHED) {
            return;
        }

        if (status == GameStatus.NOT_STARTED) {
            status = GameStatus.RUNNING;
            lastTimeMillis = System.currentTimeMillis();
        }

        processEnteredWord(enteredWord);

        refillWords();
    }

    protected abstract long getInitialTimeMillis();

    protected abstract void processEnteredWord(String enteredWord);

    public void updateGame() {
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
        if (status == GameStatus.RUNNING) {
            updateTime();
        }
    }

    private void updateTime() {
        long currentTimeMillis = System.currentTimeMillis();
        timeLeftMillis -= (currentTimeMillis - lastTimeMillis);
        lastTimeMillis = currentTimeMillis;

        finishGameIfNoTimeLeft();
    }

    private void finishGameIfNoTimeLeft() {
        if (timeLeftMillis <= 0) {
            timeLeftMillis = 0;
            status = GameStatus.FINISHED;
        }
    }

    private void refillWords() {
        while (words.size() < ACTIVE_WORDS) {
            words.add(WordFactory.create());
        }
    }
}
