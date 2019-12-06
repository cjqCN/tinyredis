package com.github.cjqcn.tinyredis.core.client.impl

import com.github.cjqcn.tinyredis.core.client.RedisClient
import com.github.cjqcn.tinyredis.core.command.RedisCommand
import com.github.cjqcn.tinyredis.core.command.RedisResponse
import com.github.cjqcn.tinyredis.core.db.RedisDb
import com.github.cjqcn.tinyredis.core.server.RedisServer
import com.github.cjqcn.tinyredis.core.server.impl.RedisServerImpl
import org.slf4j.LoggerFactory

class RedisClientImpl(private val server: RedisServer, private val name: String, var curDb: RedisDb) : RedisClient {

    companion object {
        private val LOGGER = LoggerFactory.getLogger(RedisServerImpl::class.java)
    }

    init {
        LOGGER.info("RedisClient:{} init", name())
    }

    override fun server(): RedisServer {
        return server;
    }

    override fun destroy() {
        LOGGER.info("RedisClient:{} destroy", name())
        server().destroyClient(this)
    }

    override fun name(): String {
        return name
    }

    override fun db(): RedisDb {
        return curDb
    }

    override fun execute(redisCommand: RedisCommand): RedisResponse {
        return redisCommand.execute()
    }
}