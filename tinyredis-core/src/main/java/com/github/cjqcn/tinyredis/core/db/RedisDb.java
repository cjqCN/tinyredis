package com.github.cjqcn.tinyredis.core.db;

import com.github.cjqcn.tinyredis.core.struct.RedisObject;

public interface RedisDb {
    int id();

    Dict<String, RedisObject> dict();

    Dict<String, Long> expires();
}
