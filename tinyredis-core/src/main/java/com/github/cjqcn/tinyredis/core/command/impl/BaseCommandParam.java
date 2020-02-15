package com.github.cjqcn.tinyredis.core.command.impl;

import com.github.cjqcn.tinyredis.core.command.CommandParam;
import com.github.cjqcn.tinyredis.core.struct.impl.StringRedisObject;

public class BaseCommandParam implements CommandParam {
    private final Object value;

    public BaseCommandParam(Object value) {
        this.value = value;
    }

    @Override
    public Object get() {
        return value;
    }

    @Override
    public String getAsString() {
        return value.toString();
    }

    @Override
    public StringRedisObject getAsStringRedisObject() {
        return null;
    }

    @Override
    public long getAsLong() {
        if (value instanceof Number) {
            return ((Number) value).longValue();
        }
        throw new RuntimeException("value !instanceof Number");
    }

    @Override
    public int getAsInt() {
        if (value instanceof Number) {
            return ((Number) value).intValue();
        }
        throw new RuntimeException("value !instanceof Number");
    }
}
