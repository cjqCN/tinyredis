package com.github.cjqcn.tinyredis.core.exception;

public class RedisException extends RuntimeException {

    public static final RedisException WRONG_TYPE_OPERATION = new RedisException("WRONGTYPE Operation against a key holding the wrong kind of value");

    public RedisException(String errorMsg) {
        super(errorMsg);
    }
}
