package com.typewars.gameserver.util;

import com.typewars.gameserver.common.Position;
import com.typewars.gameserver.common.Size;
import com.typewars.gameserver.common.Word;

import java.util.Random;

public class WordFactory {
  private static final String HEX_SYMBOLS = "3456789abcdef";

  private static final int WORD_BOX_HEIGHT = 60;

  private static final int CANVAS_WIDTH = 1200;
  private static final int CANVAS_HEIGHT = 600;

  private static final int MINIMUM_SPEED = 2;
  private static final int MAXIMUM_SPEED = 7;

  private static final Random random = new Random();

  private WordFactory() {
  }

  public static Word create(String word) {
    Word gameWord = new Word(word);
    gameWord.setColor(generateRandomColor());
    gameWord.setVelocity(generateVelocity());
    generateAndSetSizeAndPosition(gameWord);

    return gameWord;
  }

  private static String generateRandomColor() {
    String color = "#";

    for (int i = 0; i < 6; i++) {
      color += HEX_SYMBOLS.charAt(random.nextInt(HEX_SYMBOLS.length()));
    }

    return color;
  }

  private static void generateAndSetSizeAndPosition(Word word) {
    Size size = getWordSize(word.getWord());
    word.setSize(size);
    word.setPosition(generatePositionBySize(size));
  }

  private static Size getWordSize(String word) {
    return new Size(WORD_BOX_HEIGHT, WidthMeasurer.measureWordWidth(word));
  }

  private static Position generatePositionBySize(Size size) {
    return new Position(
        random.nextInt(CANVAS_WIDTH - size.getWidth() + 1),
        random.nextInt(CANVAS_HEIGHT - size.getHeight() + 1));
  }

  private static Position generateVelocity() {
    return new Position(
        randomSignedIntInAbsoluteRange(MINIMUM_SPEED, MAXIMUM_SPEED),
        randomSignedIntInAbsoluteRange(MINIMUM_SPEED, MAXIMUM_SPEED));
  }

  private static int randomSignedIntInAbsoluteRange(int low, int up) {
    int result = randomIntInRange(low, up);

    return random.nextBoolean() ? result : -result;
  }

  private static int randomIntInRange(int low, int up) {
    return low + random.nextInt(up - low + 1);
  }
}
