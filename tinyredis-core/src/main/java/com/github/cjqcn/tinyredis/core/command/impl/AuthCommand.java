package com.github.cjqcn.tinyredis.core.command.impl;

import com.github.cjqcn.tinyredis.core.client.RedisClient;
import com.github.cjqcn.tinyredis.core.command.RedisCommand;
import com.github.cjqcn.tinyredis.core.exception.RedisException;

public class AuthCommand extends AbstractCommand implements RedisCommand {
    private String password;

    protected AuthCommand(RedisClient redisClient, String password) {
        super(redisClient);
        this.password = password;
    }

    @Override
    public void execute() {
        boolean auth = redisClient.server().auth(password);
        if (auth) {
            redisClient.dataAccess().setAuth(true);
            redisClient.stream().responseString("OK");
        } else {
            throw RedisException.AUTH_ERROR;
        }
    }

    @Override
    public String decode() {
        return "auth " + password;
    }

    public static AuthCommand build(RedisClient redisClient, String password) {
        return new AuthCommand(redisClient, password);
    }

    @Override
    public String toString() {
        return "AuthCommand{" +
                password +
                '}';
    }
}
