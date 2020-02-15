package com.github.cjqcn.tinyredis.remote.client;

import com.github.cjqcn.tinyredis.core.client.RedisClient;
import com.github.cjqcn.tinyredis.core.command.CommandType;
import com.github.cjqcn.tinyredis.core.command.RedisCommand;
import com.github.cjqcn.tinyredis.core.command.impl.AuthCommand;
import com.github.cjqcn.tinyredis.core.command.impl.GetCommand;
import com.github.cjqcn.tinyredis.core.command.impl.SelectDbCommand;
import com.github.cjqcn.tinyredis.core.command.impl.SetCommand;
import com.github.cjqcn.tinyredis.core.struct.impl.StringRedisObject;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.CodecException;
import io.netty.handler.codec.redis.*;
import io.netty.util.CharsetUtil;

public class CommandBuilder {

    /**
     * TODO 参数校验
     *
     * @param redisClient
     * @param messages
     * @return
     */
    public static final RedisCommand decode(RedisClient redisClient, FullBulkStringRedisMessage[] messages) {
        String commandTag = decodeMessageToString(messages[0]).toLowerCase();
        switch (commandTag) {
            case CommandType.AUTH:
                return AuthCommand.build(redisClient, decodeMessageToString(messages[1]));
            case CommandType.SELECT:
                return SelectDbCommand.build(redisClient, Integer.parseInt(decodeMessageToString(messages[1])));
            case CommandType.GET:
                return GetCommand.build(redisClient, decodeMessageToString(messages[1]));
            case CommandType.SET:
                return SetCommand.build(redisClient, decodeMessageToString(messages[1]), decodeMessageToString(messages[2]), Long.parseLong(decodeMessageToString(messages[3])));
            default:
                return null;
        }
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
