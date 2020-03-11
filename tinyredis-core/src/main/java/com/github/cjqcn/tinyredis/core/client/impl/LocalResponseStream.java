package com.github.cjqcn.tinyredis.core.client.impl;

import com.github.cjqcn.tinyredis.core.client.RedisResponseStream;
import com.github.cjqcn.tinyredis.core.command.RedisResponse;

public class LocalResponseStream implements RedisResponseStream {
    @Override
    public void response(RedisResponse msg) {
        System.out.println(msg.decode());
    }

    @Override
    public void responseString(String str) {
        System.out.println(str);
    }

    @Override
    public void response(Object object) {
        System.out.println(object);
    }

    @Override
    public void error(String error) {
        System.err.println(error);
    }
}
