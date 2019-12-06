package com.github.cjqcn.tinyredis.core.server

import com.github.cjqcn.tinyredis.core.client.RedisClient

interface RedisServer {
    fun info(): RedisInfo;
    fun createClient(): RedisClient
    fun getClient(name: String): RedisClient?
    fun destroyClient(redisClient: RedisClient)
}