package com.typewars.controller;


import com.typewars.model.CreateGameResponse;
import com.typewars.model.GameState;
import com.typewars.service.SurvivalGameService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

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