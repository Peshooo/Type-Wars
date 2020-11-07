package com.typewars.gameserver.controller;

import com.typewars.gameserver.common.CreateGameResponse;
import com.typewars.gameserver.common.GameState;
import com.typewars.gameserver.service.SurvivalGameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/survival")
public class SurvivalGameController implements GameController {
  private final SurvivalGameService survivalGameService;

  public SurvivalGameController(SurvivalGameService survivalGameService) {
    this.survivalGameService = survivalGameService;
  }

  @Override
  @PostMapping
  public CreateGameResponse createGame(@RequestParam String nickname) {
    return new CreateGameResponse(survivalGameService.createGame(nickname));
  }

  @Override
  @GetMapping("/{gameId}")
  public GameState getGameState(@PathVariable String gameId) {
    return survivalGameService.getGameState(gameId);
  }

  @Override
  @PutMapping("/{gameId}")
  public void processEnteredWord(@PathVariable String gameId, @RequestParam String word) {
    survivalGameService.processEnteredWord(gameId, word);
  }
}
