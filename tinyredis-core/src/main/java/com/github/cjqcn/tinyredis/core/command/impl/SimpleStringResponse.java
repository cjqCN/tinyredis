package com.github.cjqcn.tinyredis.core.command.impl;

import com.github.cjqcn.tinyredis.core.command.RedisResponse;

public class SimpleStringResponse implements RedisResponse {

    public static final RedisResponse OK = new SimpleStringResponse("OK");

    public static final RedisResponse NULL = new SimpleStringResponse("(nil)");

    protected final String content;

    private SimpleStringResponse(String content) {
        this.content = content;
    }

    public static SimpleStringResponse valueOf(String value) {
        return new SimpleStringResponse(value);
    }


    @Override
    public String decode() {
        return content;
    }
}
