package com.typewars.gameserver.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class WordProvider {
  private static final Logger logger = LoggerFactory.getLogger(WordProvider.class);

  private static final File file =
      new File(WordProvider.class
          .getClassLoader()
          .getResource("words.txt")
          .getFile());

  private static final List<String> words = readWordsFromFile(file);
  private static final Random random = new Random();

  private WordProvider() {
  }

  private static List<String> readWordsFromFile(File file) {
    List<String> words = new ArrayList<>();
    readWordsFromFile(file, words);

    return words;
  }

  private static void readWordsFromFile(File file, List<String> words) {
    try (Scanner scanner = new Scanner(file)) {
      readWordsWithScanner(scanner, words);
    } catch(FileNotFoundException e) {
      logErrorAndThrowRuntimeException("Cannot find words file.");
    }
  }

  private static void readWordsWithScanner(Scanner scanner, List<String> words) {
    while (scanner.hasNextLine()) {
      words.add(scanner.nextLine());
    }
  }

  private static void logErrorAndThrowRuntimeException(String errorMessage) {
    logger.error(errorMessage);
    throw new RuntimeException(errorMessage);
  }

  public static String getWord() {
    int wordIdx = random.nextInt(words.size());
    return words.get(wordIdx);
  }
}
