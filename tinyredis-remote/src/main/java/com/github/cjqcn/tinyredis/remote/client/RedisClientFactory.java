package com.github.cjqcn.tinyredis.remote.client;

import com.github.cjqcn.tinyredis.core.client.RedisClient;
import com.github.cjqcn.tinyredis.core.client.impl.RedisClientImpl;
import io.netty.channel.ChannelHandlerContext;

import java.util.concurrent.atomic.AtomicLong;

public class RedisClientFactory {
    private static final AtomicLong counter = new AtomicLong(0);

    public static RedisClient createRedisClient(ChannelHandlerContext ctx) {
        return new RedisClientImpl(newName(), new RemoteRedisResponseStream(ctx));
    }

    private static String newName() {
        return "client-" + counter.getAndIncrement();
    }


}
