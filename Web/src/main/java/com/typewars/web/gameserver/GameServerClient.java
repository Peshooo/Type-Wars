package com.typewars.web.gameserver;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

@Component
public class GameServerClient {
  private final Logger logger = LoggerFactory.getLogger(getClass());

  private final RestTemplate restTemplate;
  private final String baseUrl;

  public GameServerClient(@Value("${game-server.base-url}") String baseUrl) {
    logger.info("base url is {}", baseUrl);
    restTemplate = new RestTemplate();
    this.baseUrl = baseUrl;
  }

  public void log(String text) {
    logger.info(text);
  }

  public CreateGameResponse createGame(String nickname) {
    String url = buildCreateGameUrl(nickname);
    logger.info("url is {}", url);
    CreateGameResponse gameResponse = restTemplate.postForObject(url, null, CreateGameResponse.class);
    logger.info("id is {}", gameResponse.getGameId());
    return gameResponse;
  }

  public GameState getGameState(String gameId) {
    return restTemplate.getForObject(buildGetGameStateUrl(gameId), GameState.class);
  }

  public void enterWord(String gameId, String word) {
    logger.info("Game {} entered {}", gameId, word);
    restTemplate.put(buildEnteredWordUrl(gameId, word), null);
  }

  private String buildCreateGameUrl(String nickname) {
    return UriComponentsBuilder.fromHttpUrl(baseUrl)
        .queryParam("nickname", nickname)
        .toUriString();
  }

  private String buildGetGameStateUrl(String gameId) {
    return UriComponentsBuilder.fromHttpUrl(baseUrl)
        .pathSegment(gameId)
        .toUriString();
  }

  private String buildEnteredWordUrl(String gameId, String word) {
    return UriComponentsBuilder.fromHttpUrl(baseUrl)
        .pathSegment(gameId)
        .queryParam("word", word)
        .toUriString();
  }
}