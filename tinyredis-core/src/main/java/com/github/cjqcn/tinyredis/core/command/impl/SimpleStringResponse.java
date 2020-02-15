package com.github.cjqcn.tinyredis.core.command.impl;

import com.github.cjqcn.tinyredis.core.command.RedisResponse;

public class SimpleStringResponse implements RedisResponse {

    public static final RedisResponse OK = new SimpleStringResponse("OK");

    public static final RedisResponse GET_NULL = new SimpleStringResponse("NULL");

    protected final String content;

    public SimpleStringResponse(String content) {
        this.content = content;
    }


    @Override
    public String decode() {
        return content;
    }
}