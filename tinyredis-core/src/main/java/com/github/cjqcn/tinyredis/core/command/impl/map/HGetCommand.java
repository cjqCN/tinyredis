package com.github.cjqcn.tinyredis.core.command.impl.map;

import com.github.cjqcn.tinyredis.core.client.RedisClient;
import com.github.cjqcn.tinyredis.core.command.RedisCommand;
import com.github.cjqcn.tinyredis.core.command.impl.AbstractCommand;

public class HGetCommand extends AbstractCommand implements RedisCommand {


    protected HGetCommand(RedisClient redisClient) {
        super(redisClient);
    }

    @Override
    public void execute() {

    }

    @Override
    public String decode() {
        return null;
    }
}
