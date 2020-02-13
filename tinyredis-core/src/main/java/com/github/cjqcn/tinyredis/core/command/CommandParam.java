package com.github.cjqcn.tinyredis.core.command;

import java.util.function.Supplier;

public interface CommandParam<T> extends Supplier<T> {
    @Override
    T get();

    String getAsString();

    long getAsLong();

    int getAsInt();

}
