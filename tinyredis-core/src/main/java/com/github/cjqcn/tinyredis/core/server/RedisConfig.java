package com.github.cjqcn.tinyredis.core.server;

public interface RedisConfig {
    RedisConfig setHost(String host);

    RedisConfig setPort(int port);

    RedisConfig setDbNum(int dbNum);

    RedisConfig setPassword(String password);

    String getHost();

    int getPort();

    int getDbNum();

    String getPassword();
}
