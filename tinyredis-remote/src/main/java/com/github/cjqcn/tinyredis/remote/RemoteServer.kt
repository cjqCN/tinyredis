package com.github.cjqcn.tinyredis.remote

import com.github.cjqcn.tinyredis.core.server.impl.RedisServerImpl
import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.ChannelInitializer
import io.netty.channel.ChannelOption
import io.netty.channel.EventLoopGroup
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.handler.codec.redis.RedisArrayAggregator
import io.netty.handler.codec.redis.RedisBulkStringAggregator
import io.netty.handler.codec.redis.RedisDecoder
import io.netty.handler.codec.redis.RedisEncoder
import io.netty.handler.logging.LogLevel
import io.netty.handler.logging.LoggingHandler
import io.netty.handler.ssl.SslContext
import io.netty.handler.ssl.SslContextBuilder
import io.netty.handler.ssl.util.SelfSignedCertificate

class RemoteServer {

    companion object {
        private val SSL = System.getProperty("ssl") != null
        private val PORT = System.getProperty("port", "6379").toInt()
    }

    private val redis = RedisServerImpl()

    @Throws(Exception::class)
    fun start() { // Configure SSL.
        val sslCtx: SslContext?
        sslCtx = if (SSL) {
            val ssc = SelfSignedCertificate()
            SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build()
        } else {
            null
        }
        // Configure the server.
        val bossGroup: EventLoopGroup = NioEventLoopGroup(1)
        val workerGroup: EventLoopGroup = NioEventLoopGroup()
        try {
            val b = ServerBootstrap()
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel::class.java)
                    .option(ChannelOption.SO_BACKLOG, 100)
                    .handler(LoggingHandler(LogLevel.INFO))
                    .childHandler(object : ChannelInitializer<SocketChannel>() {
                        @Throws(Exception::class)
                        override fun initChannel(ch: SocketChannel) {
                            val p = ch.pipeline()
                            if (sslCtx != null) {
                                p.addLast(sslCtx.newHandler(ch.alloc()))
                            }
                            p.addLast(LoggingHandler(LogLevel.INFO));
                            p.addLast(RedisEncoder())
                            p.addLast(RedisDecoder())
                            p.addLast(RedisBulkStringAggregator())
                            p.addLast(RedisArrayAggregator())
                            p.addLast(RedisServerHandler(redis))
                        }
                    })
            // Start the server.
            val f = b.bind(PORT).sync()
            // Wait until the server socket is closed.
            f.channel().closeFuture().sync()
        } finally { // Shut down all event loops to terminate all threads.
            bossGroup.shutdownGracefully()
            workerGroup.shutdownGracefully()
        }
    }

}