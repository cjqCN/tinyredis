package com.github.cjqcn.tinyredis.remote;

import com.github.cjqcn.tinyredis.core.server.RedisServer;
import com.github.cjqcn.tinyredis.core.server.impl.RedisServerImpl;
import com.github.cjqcn.tinyredis.remote.client.RedisClientHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.redis.RedisArrayAggregator;
import io.netty.handler.codec.redis.RedisBulkStringAggregator;
import io.netty.handler.codec.redis.RedisDecoder;
import io.netty.handler.codec.redis.RedisEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.handler.ssl.SslContext;
import io.netty.handler.ssl.SslContextBuilder;
import io.netty.handler.ssl.util.SelfSignedCertificate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;


public final class RemoteServer {

    private static final boolean SSL = System.getProperty("ssl") != null;
    private static final Logger logger = LoggerFactory.getLogger(RemoteServer.class);

    public static void main(String[] args) throws Exception {
        long start = System.currentTimeMillis();
        final RedisServer server = new RedisServerImpl();
        server.init();
        String host = server.redisConfig().getHost();
        int port = server.redisConfig().getPort();
        InetSocketAddress bindAddress = new InetSocketAddress(host, port);

        // Configure SSL.
        final SslContext sslCtx;
        if (SSL) {
            SelfSignedCertificate ssc = new SelfSignedCertificate();
            sslCtx = SslContextBuilder.forServer(ssc.certificate(), ssc.privateKey()).build();
        } else {
            sslCtx = null;
        }

        // Configure the server.
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
                    .channel(NioServerSocketChannel.class)
                    .option(ChannelOption.SO_BACKLOG, 100)
                    .handler(new LoggingHandler(LogLevel.DEBUG))
                    .childHandler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        public void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline p = ch.pipeline();
                            if (sslCtx != null) {
                                p.addLast(sslCtx.newHandler(ch.alloc()));
                            }
                            p.addLast(new RedisEncoder());
                            p.addLast(new RedisDecoder());
                            p.addLast(new RedisBulkStringAggregator());
                            p.addLast(new RedisArrayAggregator());
                            p.addLast(new RedisClientHandler(server));
                        }
                    });

            // Start the server.
            ChannelFuture f = b.bind(bindAddress).sync();
            long end = System.currentTimeMillis();
            logger.info("Started redis at address {}, cost {}ms", bindAddress, end - start);
            // Wait until the server socket is closed.
            f.channel().closeFuture().sync();
        } finally {
            // Shut down all event loops to terminate all threads.
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }
}
