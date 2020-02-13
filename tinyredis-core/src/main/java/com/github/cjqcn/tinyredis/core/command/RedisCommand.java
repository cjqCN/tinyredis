package com.github.cjqcn.tinyredis.core.command;

import com.github.cjqcn.tinyredis.core.client.RedisClient;

public interface RedisCommand {
    void execute(RedisClient client);

    CommandParam[] commandParams();

    CommandType type();

    StatsInfo stats();
}
