package com.typewars.web.gameserver;

public class Word {
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
}