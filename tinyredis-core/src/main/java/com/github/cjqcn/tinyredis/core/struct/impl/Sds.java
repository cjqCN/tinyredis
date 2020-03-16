package com.github.cjqcn.tinyredis.core.struct.impl;

import com.github.cjqcn.tinyredis.core.struct.Type;
import com.github.cjqcn.tinyredis.core.struct.RedisObject;

import java.util.Objects;

public class Sds implements RedisObject<String> {

    public static final Sds valueOf(String content) {
        return new Sds(content);
    }

    public static final Sds valueOf(Number number) {
        return new Sds(number);
    }

    private final String content;

    public Sds(String content) {
        this.content = content;
    }

    public Sds(Number number) {
        this.content = number.toString();
    }

    public String content() {
        return content;
    }

    @Override
    public Type type() {
        return Type.string;
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
        Sds that = (Sds) o;
        return content.equals(that.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content);
    }

}
