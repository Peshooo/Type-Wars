package com.typewars.model;

import java.io.Serializable;

public class Position implements Serializable {
    private float x;
    private float y;

    public Position() {
    }

    public Position(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void move(Position delta) {
        x += delta.getX();
        y += delta.getY();
    }

    public Position scaled(float factor) {
        return new Position(x * factor, y * factor);
    }
}