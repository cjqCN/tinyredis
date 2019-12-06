package com.github.cjqcn.tinyredis.core.command.impl

import com.github.cjqcn.tinyredis.core.client.RedisClient
import com.github.cjqcn.tinyredis.core.command.RedisCommand
import com.github.cjqcn.tinyredis.core.command.RedisResponse
import com.github.cjqcn.tinyredis.core.struct.RedisObject
import com.github.cjqcn.tinyredis.core.struct.TtlObject

class SetCommand(private val redisClient: RedisClient, private val key: RedisObject, private val value: RedisObject, private val expire: Long?) : RedisCommand {

    override fun client(): RedisClient {
        return redisClient;
    }

    override fun execute(): RedisResponse {
        val db = client().db()
        db.dict().set(key, value)
        if (expire != null) {
            db.expires().set(key, TtlObject(expire + System.currentTimeMillis()));
        }
        return CommonResponse.OK;

    }

    override fun name(): String {
        return "set";
    }
}