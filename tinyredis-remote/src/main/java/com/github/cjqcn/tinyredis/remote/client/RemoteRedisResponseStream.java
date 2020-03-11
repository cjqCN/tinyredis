package com.github.cjqcn.tinyredis.remote.client;

import com.github.cjqcn.tinyredis.core.client.RedisResponseStream;
import com.github.cjqcn.tinyredis.core.command.RedisResponse;
import com.github.cjqcn.tinyredis.core.command.impl.ArrayResponse;
import com.github.cjqcn.tinyredis.core.command.impl.SimpleStringResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.redis.ArrayRedisMessage;
import io.netty.handler.codec.redis.ErrorRedisMessage;
import io.netty.handler.codec.redis.RedisMessage;
import io.netty.handler.codec.redis.SimpleStringRedisMessage;

import java.util.ArrayList;
import java.util.List;

public class RemoteRedisResponseStream implements RedisResponseStream {
    private final ChannelHandlerContext ctx;

    RemoteRedisResponseStream(ChannelHandlerContext ctx) {
        this.ctx = ctx;
    }

    @Override
    public void response(RedisResponse msg) {
        if (msg instanceof SimpleStringResponse) {
            responseString(msg.decode());
        } else if (msg instanceof ArrayResponse) {
            ArrayResponse tmp = (ArrayResponse) msg;
            List<RedisMessage> redisMessages = new ArrayList<>(tmp.getRedisResponses().size());
            for (RedisResponse redisResponse : tmp.getRedisResponses()) {
                redisMessages.add(new SimpleStringRedisMessage(redisResponse.decode()));
            }
            ctx.write(new ArrayRedisMessage(redisMessages));
            ctx.flush();
        }
    }

    @Override
    public void responseString(String str) {
        ctx.write(new SimpleStringRedisMessage(str));
        ctx.flush();
    }

    @Override
    public void response(Object object) {
        responseString(object.toString());
    }


    @Override
    public void error(String error) {
        ctx.write(new ErrorRedisMessage(error));
        ctx.flush();
    }
}
