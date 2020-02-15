package com.github.cjqcn.tinyredis.remote.client;

import com.github.cjqcn.tinyredis.core.client.RedisResponseStream;
import com.github.cjqcn.tinyredis.core.command.RedisResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.redis.ErrorRedisMessage;
import io.netty.handler.codec.redis.SimpleStringRedisMessage;

public class RemoteRedisResponseStream implements RedisResponseStream {
    private final ChannelHandlerContext ctx;

    RemoteRedisResponseStream(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public void response(RedisResponse msg) {
        responseString(msg.decode());
    }

    @Override
    public void responseString(String str) {
        ctx.write(new SimpleStringRedisMessage(str));
        ctx.flush();
    }

    @Override
    public void error(String error) {
        ctx.write(new ErrorRedisMessage(error));
        ctx.flush();
    }
}
