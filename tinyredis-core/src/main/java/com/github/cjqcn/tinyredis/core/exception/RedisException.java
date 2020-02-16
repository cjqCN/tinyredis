package com.github.cjqcn.tinyredis.core.exception;

public class RedisException extends RuntimeException {
    public RedisException(String errorMsg) {
        super(errorMsg);
    }
}
