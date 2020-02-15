package com.github.cjqcn.tinyredis.remote.client;

import com.github.cjqcn.tinyredis.core.client.RedisClient;
import com.github.cjqcn.tinyredis.core.command.RedisCommand;
import com.github.cjqcn.tinyredis.core.exception.RedisException;
import com.github.cjqcn.tinyredis.core.server.RedisServer;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.redis.ArrayRedisMessage;
import io.netty.handler.codec.redis.FullBulkStringRedisMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RedisClientHandler extends SimpleChannelInboundHandler<ArrayRedisMessage> {

    private static final Logger LOG = LoggerFactory.getLogger(RedisClientHandler.class);
    private final RedisServer server;
    private RedisClient redisClient;

    public RedisClientHandler(RedisServer server) {
        this.server = server;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        // build redisClient
        redisClient = RedisClientFactory.createRedisClient(ctx);
        server.registerClient(redisClient);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ArrayRedisMessage msg) {
        FullBulkStringRedisMessage[] messages = new FullBulkStringRedisMessage[msg.children().size()];
        for (int i = 0; i < messages.length; i++) {
            messages[i] = (FullBulkStringRedisMessage) msg.children().get(i);
        }
        RedisCommand redisCommand = CommandBuilder.decode(redisClient, messages);
        if (redisCommand != null) {
            redisClient.executeCommand(redisCommand);
        } else {
            redisClient.stream().error("null executeCommand");
        }
    }


    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        if (cause instanceof RedisException) {
            redisClient.stream().error(cause.getMessage());
            return;
        }
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
        redisClient.destroy();
    }


}
