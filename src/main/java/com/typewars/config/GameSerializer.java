package com.typewars.config;

import com.typewars.service.Game;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.SerializationException;

public class GameSerializer implements RedisSerializer<Game> {
    @Override
    public byte[] serialize(Game game) throws SerializationException {
        return new byte[0];
    }

    @Override
    public Game deserialize(byte[] bytes) throws SerializationException {
        return null;
    }
}
