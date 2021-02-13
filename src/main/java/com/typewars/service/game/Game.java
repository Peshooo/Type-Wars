package com.typewars.service.game;

import com.typewars.model.*;
import com.typewars.service.util.WordFactory;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public abstract class Game implements Serializable {
    protected static final int ACTIVE_WORDS = 10;
    protected static final int CANVAS_WIDTH = 1200;
    protected static final int CANVAS_HEIGHT = 600;

    private GameMetadata metadata;
    private GameStatus status;
    protected long score;
    private long lastTimeMillis;
    protected long timeLeftMillis;
    protected List<Word> words;
    private final String gameMode;

    public Game(RedisGame redisGame, String gameMode) {
        metadata = new GameMetadata(redisGame.getId(), redisGame.getNickname(), redisGame.getCreatedAt());
        status = redisGame.getStatus();
        score = redisGame.getScore();
        lastTimeMillis = redisGame.getLastTimeMillis();
        timeLeftMillis = redisGame.getTimeLeftMillis();
        words = redisGame.getWords();
        this.gameMode = gameMode;
    }

    public synchronized RedisGame toRedisGame() {
        RedisGame redisGame = new RedisGame();
        redisGame.setId(metadata.getId());
        redisGame.setNickname(metadata.getNickname());
        redisGame.setCreatedAt(metadata.getCreatedAt());
        redisGame.setStatus(status);
        redisGame.setScore(score);
        redisGame.setLastTimeMillis(lastTimeMillis);
        redisGame.setTimeLeftMillis(timeLeftMillis);
        redisGame.setWords(words);
        redisGame.setGameMode(gameMode);

        return redisGame;
    }

    public Game(String id, String nickname, String gameMode) {
        long createdAt = System.currentTimeMillis();
        metadata = new GameMetadata(id, nickname, createdAt);
        status = GameStatus.NOT_STARTED;
        score = 0;
        lastTimeMillis = createdAt;
        timeLeftMillis = getInitialTimeMillis();
        words = new ArrayList<>();
        this.gameMode = gameMode;

        refillWords();
    }

    public String getGameMode() {
        return gameMode;
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

    public List<Word> getWords() {
        return words;
    }

    protected abstract long getInitialTimeMillis();

    protected abstract void processEnteredWord(String enteredWord);

    public void updateGame() {
        if (status == GameStatus.FINISHED) {
            return;
        }

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
