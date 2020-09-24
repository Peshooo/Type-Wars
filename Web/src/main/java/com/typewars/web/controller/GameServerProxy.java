package com.typewars.web.controller;

import com.typewars.web.gameserver.CreateGameResponse;
import com.typewars.web.gameserver.GameServerClient;
import com.typewars.web.gameserver.GameState;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/proxy")
public class GameServerProxy {
  private final GameServerClient gameServerClient;

  public GameServerProxy(GameServerClient gameServerClient) {
    this.gameServerClient = gameServerClient;
  }

  @PostMapping("/{gameMode}")
  public CreateGameResponse createGame(@PathVariable String gameMode, HttpServletRequest request) {
    String nickname = (String) request.getSession().getAttribute("nickname");
    return gameServerClient.createGame(gameMode, nickname);
  }

  @GetMapping("/{gameMode}/{gameId}")
  public GameState getGameState(@PathVariable String gameMode, @PathVariable String gameId) {
    return gameServerClient.getGameState(gameMode, gameId);
  }

  @PutMapping("/{gameMode}/{gameId}")
  public void enterWord(@PathVariable String gameMode, @PathVariable String gameId, @RequestParam String word) {
    gameServerClient.enterWord(gameMode, gameId, word);
  }
}