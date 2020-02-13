package com.github.cjqcn.tinyredis.core.struct.impl;

import com.github.cjqcn.tinyredis.core.struct.ObjectType;
import com.github.cjqcn.tinyredis.core.struct.RedisObject;

import java.util.Objects;

public class TtlRedisObject implements RedisObject {
    private final long ttl;


    public static final TtlRedisObject valueOf(long ttl) {
        return new TtlRedisObject(ttl);
    }

    public static final TtlRedisObject valueOfByExpire(long expire) {
        return new TtlRedisObject(System.currentTimeMillis() + expire * 1000L);
    }

    public static final TtlRedisObject valueOfByExpire(String expire) {
        return new TtlRedisObject(System.currentTimeMillis() + Long.parseLong(expire) * 1000L);
    }


    public TtlRedisObject(long ttl) {
        this.ttl = ttl;
    }

    public boolean isLive() {
        return System.currentTimeMillis() < ttl;
    }

    @Override
    public ObjectType type() {
        return ObjectType.ttl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        TtlRedisObject that = (TtlRedisObject) o;
        return ttl == that.ttl;
    }

    @Override
    public int hashCode() {
        return Objects.hash(ttl);
    }
}
