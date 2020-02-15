package com.github.cjqcn.tinyredis.core.command;

import com.github.cjqcn.tinyredis.core.struct.impl.StringRedisObject;

import java.util.function.Supplier;

public interface CommandParam<T> extends Supplier<T> {
    @Override
    T get();

    String getAsString();

    StringRedisObject getAsStringRedisObject();

    long getAsLong();

    int getAsInt();

}
