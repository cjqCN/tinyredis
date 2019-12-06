package com.github.cjqcn.tinyredis.remote

import com.github.cjqcn.tinyredis.core.client.RedisClient
import com.github.cjqcn.tinyredis.core.command.RedisCommand
import com.github.cjqcn.tinyredis.core.command.impl.GetCommand
import com.github.cjqcn.tinyredis.core.command.impl.SetCommand
import com.github.cjqcn.tinyredis.core.server.RedisServer
import com.github.cjqcn.tinyredis.core.struct.StringObject
import io.netty.channel.ChannelHandler
import io.netty.channel.ChannelHandlerContext
import io.netty.channel.ChannelInboundHandlerAdapter
import io.netty.handler.codec.CodecException
import io.netty.handler.codec.redis.*
import io.netty.util.CharsetUtil
import org.slf4j.LoggerFactory

@ChannelHandler.Sharable
class RedisServerHandler(redisServer: RedisServer) : ChannelInboundHandlerAdapter() {
    companion object {
        private val LOGGER = LoggerFactory.getLogger(RedisServerHandler::class.java)
    }

    private val redisClient: RedisClient = redisServer.createClient();

    override fun handlerRemoved(ctx: ChannelHandlerContext?) {
        super.handlerRemoved(ctx)
        redisClient.destroy()
    }

    override fun channelRead(ctx: ChannelHandlerContext, msg: Any?) {
        if (msg !is RedisMessage) {
            return
        }
        val command = decodeRedisCommand(msg)
        val response = command?.execute()
        ctx.write(SimpleStringRedisMessage(response?.decode() ?: "NON"))
    }

    override fun channelReadComplete(ctx: ChannelHandlerContext) {
        ctx.flush()
    }

    override fun exceptionCaught(ctx: ChannelHandlerContext, cause: Throwable) {
        cause.printStackTrace()
        ctx.close()
    }

    private fun decodeRedisCommand(msg: RedisMessage): RedisCommand? {
        if (msg !is ArrayRedisMessage) {
            return null
        }
        val first = msg.children()[0];
        if (first !is FullBulkStringRedisMessage) {
            return null
        }
        when (getString(first)) {
            "get" -> return GetCommand(redisClient, StringObject(decodeToString(msg.children()[1])))
            "set" -> return SetCommand(redisClient, StringObject(decodeToString(msg.children()[1])),
                    StringObject(decodeToString(msg.children()[2])), 10000L)
        }
        return null
    }

    private fun decodeToString(msg: RedisMessage): String {
        return when (msg) {
            is SimpleStringRedisMessage -> {
                msg.content()
            }
            is ErrorRedisMessage -> {
                msg.content()
            }
            is IntegerRedisMessage -> {
                msg.value().toString()
            }
            is FullBulkStringRedisMessage -> {
                getString(msg)
            }
            else -> {
                throw CodecException("unknown message type: $msg")
            }
        }
    }

    private fun getString(msg: FullBulkStringRedisMessage): String {
        return if (msg.isNull) {
            "(null)"
        } else msg.content().toString(CharsetUtil.UTF_8)
    }
}