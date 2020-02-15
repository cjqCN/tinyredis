package com.github.cjqcn.tinyredis.core.exception;

public class RedisException extends RuntimeException {

    public static final RedisException WRONG_TYPE_OPERATION = new RedisException("WRONGTYPE Operation against a key holding the wrong kind of value.");

    public static final RedisException ERROR_PARAM = new RedisException("error params.");

    public static final RedisException NO_AUTH = new RedisException("NOAUTH Authentication required.");

    public static final RedisException AUTH_ERROR = new RedisException("auth error.");

    public static final RedisException INVALID_EXPIRE_TIME_IN_SETEX = new RedisException("invalid expire time in SETEX");


    public RedisException(String errorMsg) {
        super(errorMsg);
    }
}
