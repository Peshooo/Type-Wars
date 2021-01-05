package com.typewars.controller;

import com.typewars.model.CreateGameResponse;
import com.typewars.model.GameState;

import javax.servlet.http.HttpServletRequest;

public interface GameController {
    CreateGameResponse createGame(HttpServletRequest request);

    GameState getGameState(String gameId);

    void processEnteredWord(String gameId, String word);
}