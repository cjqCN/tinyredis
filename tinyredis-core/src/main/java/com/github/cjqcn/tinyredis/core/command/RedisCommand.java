package com.github.cjqcn.tinyredis.core.command;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public class RedisCommand {
    private String name;
    private RedisCommandProc redisCommandProc;
    private int arity;
    private String sflags;
    private int flags;
    private RedisGetKeysProc redisGetKeysProc;
    private int firstKey;
    private int lastKey;
    private int keystep;
    private long microseconds;
    private long calls;

    public RedisCommand(String name, RedisCommandProc redisCommandProc, int arity, String sflags,
                        int flags, RedisGetKeysProc redisGetKeysProc, int firstKey,
                        int lastKey, int keystep, long microseconds, long calls) {
        this.name = name;
        this.redisCommandProc = redisCommandProc;
        this.arity = arity;
        this.sflags = sflags;
        this.flags = flags;
        this.redisGetKeysProc = redisGetKeysProc;
        this.firstKey = firstKey;
        this.lastKey = lastKey;
        this.keystep = keystep;
        this.microseconds = microseconds;
        this.calls = calls;
    }



}
