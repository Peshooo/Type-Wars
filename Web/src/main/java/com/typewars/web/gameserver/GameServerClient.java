package com.typewars.web.gameserver;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class GameServerClient {
  private final RestTemplate restTemplate;
  private final String baseUrl;

  public GameServerClient(@Value("${game-server.base-url}") String baseUrl) {
    restTemplate = new RestTemplate();
    this.baseUrl = baseUrl;
  }

  public CreateGameResponse createGame(String gameMode, String nickname) {
    String url = buildCreateGameUrl(gameMode, nickname);
    CreateGameResponse gameResponse = restTemplate.postForObject(url, null, CreateGameResponse.class);

    return gameResponse;
  }

  public GameState getGameState(String gameMode, String gameId) {
    return restTemplate.getForObject(buildGetGameStateUrl(gameMode, gameId), GameState.class);
  }

  public void enterWord(String gameMode, String gameId, String word) {
    restTemplate.put(buildEnteredWordUrl(gameMode, gameId, word), null);
  }

  private UriComponentsBuilder buildUrlWithGameMode(String gameMode) {
    return UriComponentsBuilder.fromHttpUrl(baseUrl)
        .pathSegment(gameMode);
  }

  private String buildCreateGameUrl(String gameMode, String nickname) {
    return uriBuilderFromGameMode(gameMode)
        .queryParam("nickname", nickname)
        .toUriString();
  }

  private String buildGetGameStateUrl(String gameMode, String gameId) {
    return uriBuilderFromGameMode(gameMode)
        .pathSegment(gameId)
        .toUriString();
  }

  private String buildEnteredWordUrl(String gameMode, String gameId, String word) {
    return uriBuilderFromGameMode(gameMode)
        .pathSegment(gameId)
        .queryParam("word", word)
        .toUriString();
  }

  private UriComponentsBuilder uriBuilderFromGameMode(String gameMode) {
    return UriComponentsBuilder.fromHttpUrl(baseUrl)
        .pathSegment(gameMode);
  }
}