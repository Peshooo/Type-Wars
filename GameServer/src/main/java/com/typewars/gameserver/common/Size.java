package com.typewars.gameserver.common;

public class Size extends PrintableJson {
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

  public void setHeight(int height) {
    this.height = height;
  }

  public int getWidth() {
    return width;
  }

  public void setWidth(int width) {
    this.width = width;
  }
}