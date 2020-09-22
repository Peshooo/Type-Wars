package com.typewars.gameserver.recordstore;

import com.typewars.gameserver.model.SurvivalRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class RecordStoreClient {
  private final String baseUrl;
  private final String survivalEndpoint;
  private final RestTemplate restTemplate;

  public RecordStoreClient(
      @Value("${record-store.base-url}") String baseUrl,
      @Value("${record-store.survival-endpoint}") String survivalEndpoint) {
    this.baseUrl = baseUrl;
    this.survivalEndpoint = survivalEndpoint;
    restTemplate = new RestTemplate();
  }

  public void saveSurvivalRecord(SurvivalRecord survivalRecord) {
    String url = UriComponentsBuilder.fromHttpUrl(baseUrl)
        .pathSegment(survivalEndpoint)
        .build()
        .toString();

    restTemplate.postForLocation(url, survivalRecord);
  }
}