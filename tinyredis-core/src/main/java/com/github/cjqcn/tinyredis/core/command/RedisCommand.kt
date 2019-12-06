package com.github.cjqcn.tinyredis.core.command

import com.github.cjqcn.tinyredis.core.client.RedisClient

interface RedisCommand {
    fun client(): RedisClient
    fun execute(): RedisResponse
    fun name(): String
}