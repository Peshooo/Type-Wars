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

  @PostMapping
  public CreateGameResponse createGame(HttpServletRequest request) {
    String nickname = (String) request.getSession().getAttribute("nickname");
    return gameServerClient.createGame(nickname);
  }

  @GetMapping("/{gameId}")
  public GameState getGameState(@PathVariable String gameId) {
    return gameServerClient.getGameState(gameId);
  }

  @PutMapping("/{gameId}")
  public void enterWord(@PathVariable String gameId, @RequestParam String word) {
    gameServerClient.enterWord(gameId, word);
  }
}