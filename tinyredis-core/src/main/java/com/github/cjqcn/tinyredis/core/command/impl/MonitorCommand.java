package com.github.cjqcn.tinyredis.core.command.impl;

import com.github.cjqcn.tinyredis.core.client.RedisClient;
import com.github.cjqcn.tinyredis.core.command.RedisCommand;
import com.github.cjqcn.tinyredis.core.listen.impl.AbstractListener;

public class MonitorCommand extends AbstractCommand implements RedisCommand {

    protected MonitorCommand(RedisClient redisClient) {
        super(redisClient);
    }

    @Override
    public void execute0() {
        redisClient.addListener(new AbstractListener<RedisCommand>() {
            @Override
            public void accept0(RedisCommand event) {
                redisClient.stream().responseString(event.decode());
            }
        });
        redisClient.stream().response(SimpleStringResponse.OK);
    }

    @Override
    public String decode() {
        return "monitor";
    }

    public static MonitorCommand build(RedisClient redisClient) {
        return new MonitorCommand(redisClient);
    }
}