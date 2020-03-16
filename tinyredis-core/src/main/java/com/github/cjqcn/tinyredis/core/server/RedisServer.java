package com.github.cjqcn.tinyredis.core.server;

import com.github.cjqcn.tinyredis.core.client.RedisClient;
import com.github.cjqcn.tinyredis.core.listen.ListenerManager;
import com.github.cjqcn.tinyredis.core.struct.RedisDb;

public interface RedisServer {
    void registerClient(RedisClient redisClient);
    void removeClient(RedisClient redisClient);
    RedisInfo info();
    void init();
    void destroy();
    RedisDb db(int index);
    boolean auth(String password);
    ListenerManager listenerManager();
    RedisConfig redisConfig();
}
