package com.github.cjqcn.tinyredis.core.command.impl;

import com.github.cjqcn.tinyredis.core.client.RedisClient;
import com.github.cjqcn.tinyredis.core.command.CommandParam;
import com.github.cjqcn.tinyredis.core.command.CommandType;
import com.github.cjqcn.tinyredis.core.command.RedisCommand;
import com.github.cjqcn.tinyredis.core.exception.RedisException;

public class ErrorRedisCommand extends AbstractCommand implements RedisCommand {

    public static final ErrorRedisCommand instance = new ErrorRedisCommand();

    @Override
    public CommandType type() {
        return CommandType.error;
    }

    @Override
    public void execute0(RedisClient redisClient, CommandParam... params) {
        throw RedisException.WRONG_TYPE_OPERATION;
    }
}
