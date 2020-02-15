package com.github.cjqcn.tinyredis.remote.client;

import com.github.cjqcn.tinyredis.core.client.RedisClient;
import com.github.cjqcn.tinyredis.core.command.CommandType;
import com.github.cjqcn.tinyredis.core.command.RedisCommand;
import com.github.cjqcn.tinyredis.core.command.impl.*;
import com.github.cjqcn.tinyredis.core.exception.RedisException;
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
        try {
            String commandTag = decodeMessageToString(messages[0]).toLowerCase();
            switch (commandTag) {
                case CommandType.AUTH:
                    return AuthCommand.build(redisClient, decodeMessageToString(messages[1]));
                case CommandType.SELECT:
                    return SelectDbCommand.build(redisClient, Integer.parseInt(decodeMessageToString(messages[1])));
                case CommandType.MONITOR:
                    return MonitorCommand.build(redisClient);
                case CommandType.GET:
                    return GetCommand.build(redisClient, decodeMessageToString(messages[1]));
                case CommandType.SET:
                    return tryBuildSetCommand(redisClient, messages);
                case CommandType.SET_NX:
                    return SetNxCommand.build(redisClient, decodeMessageToString(messages[1]), decodeMessageToString(messages[2]));
                case CommandType.DEL:
                    return tryBuildDelCommand(redisClient, messages);
                case CommandType.EXPIRE:
                    return ExpireCommand.build(redisClient, decodeMessageToString(messages[1]), Long.parseLong(decodeMessageToString(messages[2])));
                case CommandType.TTL:
                    return TTLCommand.build(redisClient, decodeMessageToString(messages[1]));
                default:
                    return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            throw RedisException.ERROR_PARAM;
        }
    }


    private static SetCommand tryBuildSetCommand(RedisClient redisClient, FullBulkStringRedisMessage[] messages) {
        if (messages.length == 3) {
            return SetCommand.build(redisClient, decodeMessageToString(messages[1]), decodeMessageToString(messages[2]), -1);
        }
        if (messages.length == 5 && "ex".equalsIgnoreCase(decodeMessageToString(messages[3]))) {
            return SetCommand.build(redisClient, decodeMessageToString(messages[1]), decodeMessageToString(messages[2]), Long.parseLong(decodeMessageToString(messages[4])));
        }
        throw RedisException.ERROR_PARAM;
    }

    private static DelCommand tryBuildDelCommand(RedisClient redisClient, FullBulkStringRedisMessage[] messages) {
        String[] keys = new String[messages.length - 1];
        for (int i = 1; i < messages.length; i++) {
            keys[i - 1] = decodeMessageToString(messages[i]);
        }
        return DelCommand.build(redisClient, keys);
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
