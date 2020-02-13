package com.github.cjqcn.tinyredis.core.server;

import com.github.cjqcn.tinyredis.core.client.RedisClient;
import com.github.cjqcn.tinyredis.core.db.RedisDb;

public interface RedisServer {
    void registerClient(RedisClient redisClient);

    void removeClient(RedisClient redisClient);

    RedisInfo info();

    void init();

    void destroy();

    RedisDb[] dbs();
}
