package com.github.cjqcn.tinyredis.core.struct;

public interface RedisDb {
    int id();

    Dict<String, RedisObject> dict();

    Dict<String, Long> expires();
}
