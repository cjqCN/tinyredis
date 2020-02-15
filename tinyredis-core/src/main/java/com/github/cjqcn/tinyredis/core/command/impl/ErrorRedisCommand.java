package com.github.cjqcn.tinyredis.core.command.impl;

import com.github.cjqcn.tinyredis.core.client.RedisClient;
import com.github.cjqcn.tinyredis.core.command.RedisCommand;

public class ErrorRedisCommand extends AbstractCommand implements RedisCommand {

    protected ErrorRedisCommand(RedisClient redisClient) {
        super(redisClient);
    }

    @Override
    public void execute() {
        redisClient.stream().error("ErrorRedisCommand");
    }

    @Override
    public String decode() {
        return "error";
    }
}
