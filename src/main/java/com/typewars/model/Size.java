package com.typewars.model;

public class Size {
    private int height;
    private int width;

    public Size() {
    }

    public Size(int height, int width) {
        this.height = height;
        this.width = width;
    }

    public int getHeight() {
        return height;
    }

    public int getWidth() {
        return width;
    }
}