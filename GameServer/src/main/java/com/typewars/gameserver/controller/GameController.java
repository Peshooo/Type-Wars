package com.typewars.gameserver.controller;

import com.typewars.gameserver.common.CreateGameResponse;
import com.typewars.gameserver.common.GameState;
import com.typewars.gameserver.service.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/game")
public class GameController {
  private final GameService gameService;

  @Autowired
  public GameController(GameService gameService) {
    this.gameService = gameService;
  }

  @PostMapping
  public CreateGameResponse createGame(@RequestParam String nickname) {
    return new CreateGameResponse(gameService.createGame(nickname));
  }

  @GetMapping("/{gameId}")
  public GameState getGameState(@PathVariable String gameId) {
    return gameService.getGameState(gameId);
  }

  @PutMapping("/{gameId}")
  public void processEnteredWord(@PathVariable String gameId, @RequestParam String word) {
    gameService.processEnteredWord(gameId, word);
  }
}
