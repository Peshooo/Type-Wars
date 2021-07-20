package com.typewars.controller;

import com.typewars.model.GameState;
import com.typewars.service.game.StandardGameService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SubscribeMapping;
import org.springframework.stereotype.Controller;

@Controller
public class WebSocketController {
    private final StandardGameService standardGameService;

    public WebSocketController(StandardGameService standardGameService) {
        this.standardGameService = standardGameService;
    }

    @MessageMapping("/{gameId}")
    @SendTo("/game-out/{gameId}")
    public GameState onGetState(@DestinationVariable String gameId) {
        return standardGameService.getGameState(gameId);
    }
}
