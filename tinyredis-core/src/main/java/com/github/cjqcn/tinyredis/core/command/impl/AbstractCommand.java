package com.github.cjqcn.tinyredis.core.command.impl;

import com.github.cjqcn.tinyredis.core.client.RedisClient;
import com.github.cjqcn.tinyredis.core.command.RedisCommand;

public abstract class AbstractCommand implements RedisCommand {

    protected final RedisClient redisClient;

    protected AbstractCommand(RedisClient redisClient) {
        this.redisClient = redisClient;
    }

}
