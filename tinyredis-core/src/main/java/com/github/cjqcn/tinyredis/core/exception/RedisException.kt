package com.github.cjqcn.tinyredis.core.exception

class RedisException(msg: String) : RuntimeException() {
    companion object {
        val WRONG_TYPE_OPERATION = RedisException("WRONGTYPE Operation against a key holding the wrong kind of value")
    }
}