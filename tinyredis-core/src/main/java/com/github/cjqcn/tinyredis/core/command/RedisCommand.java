package com.github.cjqcn.tinyredis.core.command;

import com.github.cjqcn.tinyredis.core.listen.RedisEvent;

public interface RedisCommand extends RedisEvent {
    void execute();
    String decode();
}
