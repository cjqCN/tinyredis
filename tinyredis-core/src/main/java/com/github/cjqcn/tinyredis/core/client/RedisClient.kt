package com.github.cjqcn.tinyredis.core.client

import com.github.cjqcn.tinyredis.core.command.RedisCommand
import com.github.cjqcn.tinyredis.core.command.RedisResponse
import com.github.cjqcn.tinyredis.core.db.RedisDb
import com.github.cjqcn.tinyredis.core.server.RedisServer

interface RedisClient {
    fun server(): RedisServer
    fun destroy()
    fun name(): String
    fun db(): RedisDb
    fun execute(redisCommand: RedisCommand): RedisResponse
}