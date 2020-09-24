package com.typewars.web.recordstore;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Arrays;
import java.util.List;

@Component
public class RecordStoreClient {
  private final String baseUrl;
  private final RestTemplate restTemplate;

  public RecordStoreClient(
      @Value("${record-store.base-url}") String baseUrl) {
    this.baseUrl = baseUrl;
    restTemplate = new RestTemplate();
  }

  public List<GameRecord> getTopFiveRecords(String gameMode) {
    String url = UriComponentsBuilder.fromHttpUrl(baseUrl)
        .pathSegment(gameMode)
        .build()
        .toString();

    GameRecord[] gameRecords = restTemplate.getForObject(url, GameRecord[].class);

    return Arrays.asList(gameRecords);
  }
}