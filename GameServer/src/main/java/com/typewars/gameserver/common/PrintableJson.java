package com.typewars.gameserver.common;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.Serializable;

public abstract class PrintableJson implements Serializable {
  private static final ObjectMapper objectMapper = new ObjectMapper();

  @Override
  public synchronized String toString() {
    try {
      return objectMapper.writerWithDefaultPrettyPrinter().writeValueAsString(this);
    } catch (JsonProcessingException e) {
      return "";
    }
  }
}