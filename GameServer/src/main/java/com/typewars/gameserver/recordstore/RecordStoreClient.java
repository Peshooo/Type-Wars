package com.typewars.gameserver.recordstore;

import com.typewars.gameserver.model.GameRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class RecordStoreClient {
  private final String baseUrl;
  private final RestTemplate restTemplate;

  public RecordStoreClient(@Value("${record-store.base-url}") String baseUrl) {
    this.baseUrl = baseUrl;
    restTemplate = new RestTemplate();
  }

  public void saveRecord(String gameMode, GameRecord gameRecord) {
    String url = UriComponentsBuilder.fromHttpUrl(baseUrl)
        .pathSegment(gameMode)
        .build()
        .toString();

    restTemplate.postForLocation(url, gameRecord);
  }
}