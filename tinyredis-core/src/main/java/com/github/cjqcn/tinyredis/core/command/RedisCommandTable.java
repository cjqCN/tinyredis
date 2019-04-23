package com.github.cjqcn.tinyredis.core.command;

public class RedisCommandTable {

    public final static RedisCommand[] redisCommandTable = {
            create("get", null, 2, "r", 0, null, 1, 1, 1, 0, 0),

    };


    public static RedisCommand create(String name, RedisCommandProc redisCommandProc, int arity, String sflags,
                                      int flags, RedisGetKeysProc redisGetKeysProc, int firstKey,
                                      int lastKey, int keystep, long microseconds, long calls) {
        return new RedisCommand(name, redisCommandProc, arity, sflags, flags, redisGetKeysProc, firstKey, lastKey, keystep, microseconds, calls);
    }


}
