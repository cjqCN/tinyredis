package com.github.cjqcn.tinyredis.core.db

import com.github.cjqcn.tinyredis.core.struct.RedisObject

interface Dict {
    fun get(key: RedisObject): RedisObject?
    fun set(key: RedisObject, value: RedisObject): Boolean
    fun del(key: RedisObject): Boolean
}