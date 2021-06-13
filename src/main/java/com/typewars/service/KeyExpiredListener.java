package com.typewars.service;

import com.typewars.service.finishedgames.FinishedStandardGamesService;
import com.typewars.service.finishedgames.FinishedSurvivalGamesService;
import com.typewars.service.game.Game;
import com.typewars.service.gamesmanager.GamesManager;
import com.typewars.service.gamesmanager.RedisGamesManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.listener.KeyExpirationEventMessageListener;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.stereotype.Service;

@Service
public class KeyExpiredListener extends KeyExpirationEventMessageListener {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private static final int MESSAGE_OFFSET = 7 + RedisGamesManager.ACTIVE_GAMES_HASH_NAME_PREFIX.length();

    private final GamesManager gamesManager;
    private final FinishedStandardGamesService finishedStandardGamesService;
    private final FinishedSurvivalGamesService finishedSurvivalGamesService;

    public KeyExpiredListener(RedisMessageListenerContainer redisMessageListenerContainer,
                              GamesManager gamesManager,
                              FinishedStandardGamesService finishedStandardGamesService,
                              FinishedSurvivalGamesService finishedSurvivalGamesService) {
        super(redisMessageListenerContainer);
        this.gamesManager = gamesManager;
        this.finishedStandardGamesService = finishedStandardGamesService;
        this.finishedSurvivalGamesService = finishedSurvivalGamesService;
    }

    @Override
    public void onMessage(Message message, byte[] pattern) {
        String gameId = new String(message.getBody()).substring(MESSAGE_OFFSET);
        Game game = gamesManager.get(gameId);

        if (game.getGameMode().equals("standard")) {
            finishedStandardGamesService.afterGameFinished(game);
        } else {
            finishedSurvivalGamesService.afterGameFinished(game);
        }

        gamesManager.delete(gameId);
    }
}
