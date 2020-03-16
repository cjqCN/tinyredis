package com.github.cjqcn.tinyredis.core.struct;

public interface RedisObject<V> {
    Type type();

    V get();
}
