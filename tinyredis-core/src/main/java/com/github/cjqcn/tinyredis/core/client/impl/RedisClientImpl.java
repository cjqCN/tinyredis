package com.github.cjqcn.tinyredis.core.client.impl;

import com.github.cjqcn.tinyredis.core.client.RedisClient;
import com.github.cjqcn.tinyredis.core.client.RedisResponseStream;
import com.github.cjqcn.tinyredis.core.command.RedisCommand;
import com.github.cjqcn.tinyredis.core.command.RedisResponse;
import com.github.cjqcn.tinyredis.core.db.RedisDb;
import com.github.cjqcn.tinyredis.core.server.RedisServer;
import sun.reflect.Reflection;

public class RedisClientImpl implements RedisClient {
    protected RedisServer server;
    protected String name;
    protected RedisDb curDb;
    protected RedisResponseStream stream;
    protected boolean auth;

    public RedisClientImpl(final String name, RedisResponseStream stream) {
        this.name = name;
        this.stream = stream;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public RedisDb curDb() {
        if (curDb == null) {
            throw new RuntimeException("curDb is not set");
        }
        return curDb;
    }

    @Override
    public DataAccess dataAccess() {
        Class callerClass = Reflection.getCallerClass();
        if (!callerClass.isAssignableFrom(RedisServer.class)) {
            throw new UnsupportedOperationException();
        }
        final RedisClientImpl redisClient = this;
        return new DataAccess() {
            @Override
            public void setCurDb(RedisDb redisDb) {
                redisClient.curDb = redisDb;
            }

            @Override
            public void setServer(RedisServer server) {
                redisClient.server = server;
            }

            @Override
            public void setName(String name) {
                redisClient.name = name;
            }

            @Override
            public void setRedisResponseStream(RedisResponseStream stream) {
                redisClient.stream = stream;
            }
        };
    }

    @Override
    public int flag() {
        return 0;
    }

    @Override
    public void init() {

    }

    @Override
    public void destroy() {

    }

    @Override
    public RedisServer server() {
        if (server == null) {
            throw new RuntimeException(this + " is unregister");
        }
        return server;
    }

    public void setCurDb(RedisDb db) {
        this.curDb = db;
    }

    @Override
    public void executeCommand(RedisCommand redisCommand) {
        if (!auth) {
            error("(error) NOAUTH Authentication required.");
            return;
        }
        redisCommand.execute(this);
    }

    @Override
    public RedisResponseStream stream() {
        return stream;
    }

    public void response(RedisResponse msg) {
        stream().response(msg);
    }

    public void responseString(String str) {
        stream().responseString(str);
    }

    public void error(String error) {
        stream().error(error);
    }

}
