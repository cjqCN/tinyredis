package com.github.cjqcn.tinyredis.core.command.impl;

import com.github.cjqcn.tinyredis.core.client.RedisClient;
import com.github.cjqcn.tinyredis.core.command.RedisCommand;

public abstract class AbstractCommand implements RedisCommand {

    protected final RedisClient redisClient;
    protected long cost;

    protected AbstractCommand(RedisClient redisClient) {
        this.redisClient = redisClient;
    }

    @Override
    public void execute() {
        long start = System.nanoTime();
        execute0();
        cost = System.nanoTime() - start;
    }

    protected abstract void execute0();


}
