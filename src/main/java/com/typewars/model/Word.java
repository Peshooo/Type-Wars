package com.typewars.model;

import java.io.Serializable;

public class Word implements Serializable {
    private static final float MOVE_RATE_MILLIS = 32;

    private long lastMoveTimestamp;

    private String word;
    private String color;

    private Size size;

    private Position position;
    private Position velocity;

    public Word() {
        lastMoveTimestamp = System.currentTimeMillis();
    }

    public Word(String word) {
        this.word = word;
        lastMoveTimestamp = System.currentTimeMillis();
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }

    public Position getPosition() {
        return position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Position getVelocity() {
        return velocity;
    }

    public void setVelocity(Position velocity) {
        this.velocity = velocity;
    }

    public void move() {
        long currentTimestamp = System.currentTimeMillis();
        long timeDifferenceMillis = currentTimestamp - lastMoveTimestamp;
        float factor = calculateVelocityScaleFactor(timeDifferenceMillis);
        position.move(velocity.scaled(factor));
        lastMoveTimestamp = System.currentTimeMillis();
    }

    private float calculateVelocityScaleFactor(long timeDifferenceMillis) {
        return timeDifferenceMillis / MOVE_RATE_MILLIS;
    }
}