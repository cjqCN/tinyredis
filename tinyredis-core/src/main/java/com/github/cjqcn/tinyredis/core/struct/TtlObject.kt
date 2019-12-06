package com.github.cjqcn.tinyredis.core.struct

class TtlObject(val ttl: Long) : RedisObject {
    override fun type(): RedisObjectType {
        return RedisObjectType.ttl
    }

    fun isLive(): Boolean {
        return System.currentTimeMillis() < ttl
    }
}