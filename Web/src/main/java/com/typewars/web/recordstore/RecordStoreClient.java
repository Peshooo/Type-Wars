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
  private final String survivalEndpoint;
  private final RestTemplate restTemplate;

  public RecordStoreClient(
      @Value("${record-store.base-url}") String baseUrl,
      @Value("${record-store.survival-endpoint}") String survivalEndpoint) {
    this.baseUrl = baseUrl;
    this.survivalEndpoint = survivalEndpoint;
    restTemplate = new RestTemplate();
  }

  public List<SurvivalRecord> getTopFiveSurvivalRecords() {
    String url = UriComponentsBuilder.fromHttpUrl(baseUrl)
        .pathSegment(survivalEndpoint)
        .build()
        .toString();

    SurvivalRecord[] survivalRecords = restTemplate.getForObject(url, SurvivalRecord[].class);

    return Arrays.asList(survivalRecords);
  }
}