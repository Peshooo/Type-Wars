package com.typewars.gameserver.util;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

public class WidthMeasurer {
  private static final double WORD_WIDTH_PADDING = 10.0;
  private static final double MULTIPLIER = 5.0;

  private WidthMeasurer() {
  }

  public static int measureWordWidth(String word) {
    double widthSum = 0;

    for (int i = 0; i < word.length(); i++) {
      widthSum += LETTER_WIDTH_BASED_ON_CANVAS_CONTEXT_MEASURE_TEXT.get(word.charAt(i));
    }

    return (int) Math.round(widthSum * MULTIPLIER + WORD_WIDTH_PADDING);
  }

  private static final Map<Character, Double> LETTER_WIDTH_BASED_ON_CANVAS_CONTEXT_MEASURE_TEXT =
      new ImmutableMap.Builder<Character, Double>()
          .put('a', 5.5615234375)
          .put('b', 5.5615234375)
          .put('c', 5.0)
          .put('d', 5.5615234375)
          .put('e', 5.5615234375)
          .put('f', 2.7783203125)
          .put('g', 5.5615234375)
          .put('h', 5.5615234375)
          .put('i', 2.2216796875)
          .put('j', 2.2216796875)
          .put('k', 5.0)
          .put('l', 2.2216796875)
          .put('m', 8.330078125)
          .put('n', 5.5615234375)
          .put('o', 5.5615234375)
          .put('p', 5.5615234375)
          .put('q', 5.5615234375)
          .put('r', 3.330078125)
          .put('s', 5.0)
          .put('t', 2.7783203125)
          .put('u', 5.5615234375)
          .put('v', 5.0)
          .put('w', 7.2216796875)
          .put('x', 5.0)
          .put('y', 5.0)
          .put('z', 5.0)
          .put('A', 6.669921875)
          .put('B', 6.669921875)
          .put('C', 7.2216796875)
          .put('D', 7.2216796875)
          .put('E', 6.669921875)
          .put('F', 6.1083984375)
          .put('G', 7.7783203125)
          .put('H', 7.2216796875)
          .put('I', 2.7783203125)
          .put('J', 5.0)
          .put('K', 6.669921875)
          .put('L', 5.5615234375)
          .put('M', 8.330078125)
          .put('N', 7.2216796875)
          .put('O', 7.7783203125)
          .put('P', 6.669921875)
          .put('Q', 7.7783203125)
          .put('R', 7.2216796875)
          .put('S', 6.669921875)
          .put('T', 6.1083984375)
          .put('U', 7.2216796875)
          .put('V', 6.669921875)
          .put('W', 9.4384765625)
          .put('X', 6.669921875)
          .put('Y', 6.669921875)
          .put('Z', 6.1083984375)
          .build();
}