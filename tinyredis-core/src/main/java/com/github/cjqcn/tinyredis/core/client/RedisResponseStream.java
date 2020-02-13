package com.github.cjqcn.tinyredis.core.client;

import com.github.cjqcn.tinyredis.core.command.RedisResponse;

public interface RedisResponseStream {

    void response(RedisResponse msg);

    void responseString(String str);

    void error(String error);
}
