package com.github.cjqcn.tinyredis.core.struct;

public interface RedisObject<V> {
    ObjectType type();

    V get();
}
