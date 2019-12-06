package com.github.cjqcn.tinyredis.core.struct

interface RedisObject {
    fun type(): RedisObjectType
}