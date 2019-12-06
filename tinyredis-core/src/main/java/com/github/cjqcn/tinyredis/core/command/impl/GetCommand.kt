package com.github.cjqcn.tinyredis.core.command.impl

import com.github.cjqcn.tinyredis.core.client.RedisClient
import com.github.cjqcn.tinyredis.core.command.RedisCommand
import com.github.cjqcn.tinyredis.core.command.RedisResponse
import com.github.cjqcn.tinyredis.core.exception.RedisException
import com.github.cjqcn.tinyredis.core.struct.RedisObject
import com.github.cjqcn.tinyredis.core.struct.StringObject
import com.github.cjqcn.tinyredis.core.struct.TtlObject

class GetCommand(private val redisClient: RedisClient, private val key: RedisObject) : RedisCommand {

    override fun client(): RedisClient {
        return redisClient;
    }

    override fun execute(): RedisResponse {
        val db = client().db()
        val ttl = db.expires().get(key)
        if (ttl is TtlObject && !ttl.isLive()) {
            db.expires().del(key)
            db.dict().del(key)
        }
        val value = db.dict().get(key) ?: return GetResponse(StringObject.NULL)
        if (value !is StringObject) {
            throw RedisException.WRONG_TYPE_OPERATION
        }
        return GetResponse(value)
    }

    override fun name(): String {
        return "get";
    }
}