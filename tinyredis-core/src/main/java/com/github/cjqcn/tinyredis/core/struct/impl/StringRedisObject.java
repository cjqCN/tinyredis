package com.github.cjqcn.tinyredis.core.struct.impl;

import com.github.cjqcn.tinyredis.core.struct.ObjectType;
import com.github.cjqcn.tinyredis.core.struct.RedisObject;

import java.util.Objects;

public class StringRedisObject implements RedisObject<String> {

    public static final StringRedisObject valueOf(String content) {
        return new StringRedisObject(content);
    }

    public static final StringRedisObject valueOf(Number number) {
        return new StringRedisObject(number);
    }

    private final String content;

    public StringRedisObject(String content) {
        this.content = content;
    }

    public StringRedisObject(Number number) {
        this.content = number.toString();
    }

    public String content() {
        return content;
    }

    @Override
    public ObjectType type() {
        return ObjectType.string;
    }

    @Override
    public String get() {
        return content;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        StringRedisObject that = (StringRedisObject) o;
        return content.equals(that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content);
    }

}
