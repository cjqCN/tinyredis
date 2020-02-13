package com.github.cjqcn.tinyredis.core.db;

public interface RedisDb {
    int id();

    Dict dict();

    Dict expires();
}
