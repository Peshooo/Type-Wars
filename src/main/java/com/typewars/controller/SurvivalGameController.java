package com.typewars.controller;


import com.typewars.aspect.DailyCounter;
import com.typewars.model.CreateGameResponse;
import com.typewars.model.GameState;
import com.typewars.service.game.SurvivalGameService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static com.typewars.model.DailyCountersKeys.CREATE_SURVIVAL_GAME;

@Controller
@RequestMapping("/game/survival")
public class SurvivalGameController implements GameController {
    private final SurvivalGameService survivalGameService;

    public SurvivalGameController(SurvivalGameService survivalGameService) {
        this.survivalGameService = survivalGameService;
    }

    @Override
    @PostMapping
    @ResponseBody
    @DailyCounter(key = CREATE_SURVIVAL_GAME)
    public CreateGameResponse createGame(HttpServletRequest request) {
        String nickname = (String) request.getSession().getAttribute("nickname");

        return new CreateGameResponse(survivalGameService.createGame(nickname));
    }

    @Override
    @GetMapping("/{gameId}")
    @ResponseBody
    public GameState getGameState(@PathVariable String gameId) {
        return survivalGameService.getGameState(gameId);
    }

    @Override
    @PutMapping("/{gameId}")
    @ResponseBody
    public void processEnteredWord(@PathVariable String gameId, @RequestParam String word) {
        survivalGameService.processEnteredWord(gameId, word);
    }
}