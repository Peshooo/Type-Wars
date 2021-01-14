package com.typewars.model;

import java.io.Serializable;

public class Size implements Serializable {
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