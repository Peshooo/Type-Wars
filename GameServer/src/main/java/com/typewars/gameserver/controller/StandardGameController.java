package com.typewars.gameserver.controller;

import com.typewars.gameserver.common.CreateGameResponse;
import com.typewars.gameserver.common.GameState;
import com.typewars.gameserver.service.StandardGameService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/standard")
public class StandardGameController implements GameController {
  private final StandardGameService standardGameService;

  public StandardGameController(StandardGameService standardGameService) {
    this.standardGameService = standardGameService;
  }

  @Override
  @PostMapping
  public CreateGameResponse createGame(@RequestParam String nickname) {
    return new CreateGameResponse(standardGameService.createGame(nickname));
  }

  @Override
  @GetMapping("/{gameId}")
  public GameState getGameState(@PathVariable String gameId) {
    return standardGameService.getGameState(gameId);
  }

  @Override
  @PutMapping("/{gameId}")
  public void processEnteredWord(@PathVariable String gameId, @RequestParam String word) {
    standardGameService.processEnteredWord(gameId, word);
  }
}