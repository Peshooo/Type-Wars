package com.typewars.gameserver.common;

public class Position extends PrintableJson {
  private int x;
  private int y;

  public Position() {
  }

  public Position(int x, int y) {
    this.x = x;
    this.y = y;
  }

  public int getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
  }

  public void move(Position delta) {
    x += delta.getX();
    y += delta.getY();
  }
}