package com.github.cjqcn.tinyredis.core.client;

import com.github.cjqcn.tinyredis.core.command.RedisCommand;
import com.github.cjqcn.tinyredis.core.listen.Listener;
import com.github.cjqcn.tinyredis.core.server.RedisServer;
import com.github.cjqcn.tinyredis.core.struct.RedisDb;

public interface RedisClient {
    String name();

    int flag();

    void init();

    void destroy();

    RedisServer server();

    void executeCommand(RedisCommand redisCommand);

    RedisResponseStream stream();

    RedisDb curDb();

    DataAccess dataAccess();

    RedisCommand getCache(String command);

    void addListener(Listener listener);

    interface DataAccess {
        void setCurDb(RedisDb redisDb);

        void setServer(RedisServer server);

        void setName(String name);

        void setRedisResponseStream(RedisResponseStream stream);

        void setAuth(boolean auth);
    }

}
