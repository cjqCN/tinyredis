package com.github.cjqcn.tinyredis.core.command.impl;

import com.github.cjqcn.tinyredis.core.command.RedisResponse;

import java.util.List;

public class ArrayResponse implements RedisResponse {
    private List<RedisResponse> redisResponses;

    public ArrayResponse(List<RedisResponse> redisResponses) {
        this.redisResponses = redisResponses;
    }

    public List<RedisResponse> getRedisResponses() {
        return redisResponses;
    }

    public void setRedisResponses(List<RedisResponse> redisResponses) {
        this.redisResponses = redisResponses;
    }

    @Override
    public String decode() {
        return redisResponses.toString();
    }
}
