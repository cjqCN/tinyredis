package com.github.cjqcn.tinyredis.core.command.impl;

import com.github.cjqcn.tinyredis.core.client.RedisClient;
import com.github.cjqcn.tinyredis.core.command.CommandParam;
import com.github.cjqcn.tinyredis.core.command.RedisCommand;
import com.github.cjqcn.tinyredis.core.command.StatsInfo;

public abstract class AbstractCommand implements RedisCommand {

    private CommandParam[] commandParams;

    @Override
    public void execute(RedisClient redisClient) {
        execute0(redisClient, commandParams());
    }

    @Override
    public CommandParam[] commandParams() {
        return commandParams;
    }

    public abstract void execute0(RedisClient redisClient, CommandParam... params);

    @Override
    public StatsInfo stats() {
        return null;
    }
}
