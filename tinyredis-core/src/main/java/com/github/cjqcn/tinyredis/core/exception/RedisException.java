package com.github.cjqcn.tinyredis.core.exception;

public class RedisException extends RuntimeException {

    public static final RedisException WRONG_TYPE_OPERATION = new RedisException("WRONGTYPE Operation against a key holding the wrong kind of value.");

    public static final RedisException NO_AUTH = new RedisException("NOAUTH Authentication required.");

    public static final RedisException AUTH_ERROR = new RedisException("auth error.");



    public RedisException(String errorMsg) {
        super(errorMsg);
    }
}
