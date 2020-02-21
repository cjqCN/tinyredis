package com.github.cjqcn.tinyredis.core.command.impl.string;

import com.github.cjqcn.tinyredis.core.client.RedisClient;
import com.github.cjqcn.tinyredis.core.command.RedisCommand;
import com.github.cjqcn.tinyredis.core.command.impl.AbstractCommand;

public class MSetCommand extends AbstractCommand implements RedisCommand {


    protected MSetCommand(RedisClient redisClient) {
        super(redisClient);
    }

    @Override
    protected void execute0() {

    }

    @Override
    public String decode() {
        return null;
    }
}
