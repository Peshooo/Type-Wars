package com.typewars.controller;

import com.typewars.aspect.DailyCounter;
import com.typewars.model.CreateGameResponse;
import com.typewars.model.GameState;
import com.typewars.service.game.StandardGameService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

import static com.typewars.model.DailyCountersKeys.CREATE_STANDARD_GAME;

@Controller
@RequestMapping("/game/standard")
public class StandardGameController implements GameController {
    private final StandardGameService standardGameService;

    public StandardGameController(StandardGameService standardGameService) {
        this.standardGameService = standardGameService;
    }

    @Override
    @PostMapping
    @ResponseBody
    @DailyCounter(key = CREATE_STANDARD_GAME)
    public CreateGameResponse createGame(HttpServletRequest request) {
        String nickname = (String) request.getSession().getAttribute("nickname");

        return new CreateGameResponse(standardGameService.createGame(nickname));
    }

    @Override
    @GetMapping("/{gameId}")
    @ResponseBody
    public GameState getGameState(@PathVariable String gameId) {
        return standardGameService.getGameState(gameId);
    }

    @Override
    @PutMapping("/{gameId}")
    public void processEnteredWord(@PathVariable String gameId, @RequestParam String word) {
        standardGameService.processEnteredWord(gameId, word);
    }
}