package com.github.cjqcn.tinyredis.core.command

interface RedisResponse {
    fun decode(): String
}