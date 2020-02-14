package com.github.cjqcn.tinyredis.core.command.impl;

import com.github.cjqcn.tinyredis.core.client.RedisClient;
import com.github.cjqcn.tinyredis.core.command.CommandParam;
import com.github.cjqcn.tinyredis.core.command.CommandType;
import com.github.cjqcn.tinyredis.core.command.RedisCommand;
import com.github.cjqcn.tinyredis.core.server.RedisServer;

public class SelectDbCommand extends AbstractCommand implements RedisCommand {

    @Override
    public void execute0(RedisClient client, CommandParam... params) {
        RedisServer redisServer = client.server();
        client.dataAccess().setCurDb(redisServer.dbs()[params[0].getAsInt()]);
        client.stream().response(SimpleStringResponse.OK);
    }

    @Override
    public CommandType type() {
        return CommandType.select;
    }
}
