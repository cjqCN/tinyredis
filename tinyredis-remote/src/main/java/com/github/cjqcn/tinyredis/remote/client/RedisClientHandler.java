package com.github.cjqcn.tinyredis.remote.client;

import com.github.cjqcn.tinyredis.core.client.RedisClient;
import com.github.cjqcn.tinyredis.core.command.RedisCommand;
import com.github.cjqcn.tinyredis.core.command.impl.ErrorRedisCommand;
import com.github.cjqcn.tinyredis.core.command.impl.GetCommand;
import com.github.cjqcn.tinyredis.core.command.impl.SetCommand;
import com.github.cjqcn.tinyredis.core.exception.RedisException;
import com.github.cjqcn.tinyredis.core.struct.impl.StringRedisObject;
import com.github.cjqcn.tinyredis.core.struct.impl.TtlRedisObject;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.CodecException;
import io.netty.handler.codec.redis.*;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RedisClientHandler extends SimpleChannelInboundHandler<ArrayRedisMessage> {

    private static final Logger LOG = LoggerFactory.getLogger(RedisClientHandler.class);

    private final RedisClient redisClient;

    public RedisClientHandler(RedisClient redisClient) {
        this.redisClient = redisClient;
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ArrayRedisMessage msg) {
        FullBulkStringRedisMessage[] messages = new FullBulkStringRedisMessage[msg.children().size()];
        for (int i = 0; i < messages.length; i++) {
            messages[i] = (FullBulkStringRedisMessage) msg.children().get(i);
        }
        redisClient.executeCommand(decode(messages));
    }


    private RedisCommand decode(FullBulkStringRedisMessage[] messages) {
        String commandTag = decodeMessageToString(messages[0]);
        switch (commandTag.toLowerCase()) {
            case "get":
                return messages.length == 2 ? new GetCommand(redisClient, decodeToStringRedisObject(messages[1])) : ErrorRedisCommand.instance;
            case "set":
                return (messages.length == 3 || messages.length == 4) ?
                        new SetCommand(redisClient,
                                decodeToStringRedisObject(messages[1]), decodeToStringRedisObject(messages[2]),
                                messages.length == 4 ? TtlRedisObject.valueOfByExpire(decodeMessageToString(messages[3])) : null) :
                        null;
            default:
                return ErrorRedisCommand.instance;
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        if (cause instanceof RedisException) {
            response(ctx, cause.getMessage());
            return;
        }
        // Close the connection when an exception is raised.
        cause.printStackTrace();
        ctx.close();
        redisClient.destroy();
    }

    private static StringRedisObject decodeToStringRedisObject(RedisMessage msg) {
        return StringRedisObject.valueOf(decodeMessageToString(msg));
    }

    private static String decodeMessageToString(RedisMessage msg) {
        if (msg instanceof SimpleStringRedisMessage) {
            return ((SimpleStringRedisMessage) msg).content();
        } else if (msg instanceof ErrorRedisMessage) {
            return ((ErrorRedisMessage) msg).content();
        } else if (msg instanceof IntegerRedisMessage) {
            return String.valueOf(((IntegerRedisMessage) msg).value());
        } else if (msg instanceof FullBulkStringRedisMessage) {
            return ((FullBulkStringRedisMessage) msg).content().toString(CharsetUtil.UTF_8);
        }
        throw new CodecException("unknown message type: " + msg);

    }

    private static String getString(FullBulkStringRedisMessage msg) {
        if (msg.isNull()) {
            return "(null)";
        }
        return msg.content().toString(CharsetUtil.UTF_8);
    }

    private void response(ChannelHandlerContext ctx, String msg) {
        ctx.write(new SimpleStringRedisMessage(msg));
    }

}
