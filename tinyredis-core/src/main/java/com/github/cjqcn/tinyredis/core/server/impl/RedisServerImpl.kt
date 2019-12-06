package com.github.cjqcn.tinyredis.core.server.impl

import com.github.cjqcn.tinyredis.core.client.RedisClient
import com.github.cjqcn.tinyredis.core.client.impl.RedisClientImpl
import com.github.cjqcn.tinyredis.core.db.impl.RedisDbImpl
import com.github.cjqcn.tinyredis.core.server.RedisInfo
import com.github.cjqcn.tinyredis.core.server.RedisServer
import org.slf4j.LoggerFactory
import java.util.concurrent.atomic.AtomicLong

class RedisServerImpl : RedisServer {
    companion object {
        private val LOGGER = LoggerFactory.getLogger(RedisServerImpl::class.java)
    }
    private val counter = AtomicLong(0);
    private var dbs = Array(16) { RedisDbImpl() }
    private val clients = HashMap<String, RedisClient>();

    init {
        LOGGER.info("RedisServer init")
    }

    override fun info(): RedisInfo {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun createClient(): RedisClient {
        val newClient = RedisClientImpl(this, "redis-client-" + counter.get(), dbs[0])
        clients[newClient.name()] = newClient
        return newClient
    }

    override fun getClient(name: String): RedisClient? {
        return clients[name]
    }

    override fun destroyClient(redisClient: RedisClient) {
        clients.remove(redisClient.name())
    }
}