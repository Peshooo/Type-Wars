package com.typewars.model;

import java.io.Serializable;

public class Word implements Serializable {
    private Long lastTimeMovedMillis;

    private String word;
    private String color;

    private Size size;

    private Position position;
    private Position velocity;

    public Word() {
    }

    public Word(String word) {
        this.word = word;
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
        if (lastTimeMovedMillis == null) {
            lastTimeMovedMillis = System.currentTimeMillis();

            return;
        }

        long currentTimeMillis = System.currentTimeMillis();
        long timeDifferenceMillis = currentTimeMillis - lastTimeMovedMillis;
        float factor = timeDifferenceMillis / 32f;

        lastTimeMovedMillis = currentTimeMillis;
        position.move(velocity, factor);
    }
}