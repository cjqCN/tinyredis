package com.github.cjqcn.tinyredis.remote.client;

import com.github.cjqcn.tinyredis.core.client.RedisClient;
import com.github.cjqcn.tinyredis.core.command.CommandType;
import com.github.cjqcn.tinyredis.core.command.RedisCommand;
import com.github.cjqcn.tinyredis.core.command.impl.*;
import com.github.cjqcn.tinyredis.core.exception.ExceptionThrower;
import com.github.cjqcn.tinyredis.core.exception.RedisException;
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
        String commandTag = "unknown";
        try {
            commandTag = decodeMessageToString(messages[0]).toLowerCase();
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
                case CommandType.SETNX:
                    return SetNxCommand.build(redisClient, decodeMessageToString(messages[1]), decodeMessageToString(messages[2]));
                case CommandType.SETEX:
                    return SetExCommand.build(redisClient, decodeMessageToString(messages[1]), decodeMessageToString(messages[3]), Long.parseLong(decodeMessageToString(messages[2])));
                case CommandType.PSETEX:
                    return PSetExCommand.build(redisClient, decodeMessageToString(messages[1]), decodeMessageToString(messages[3]), Long.parseLong(decodeMessageToString(messages[2])));
                case CommandType.DEL:
                    return tryBuildDelCommand(redisClient, messages);
                case CommandType.EXPIRE:
                    return ExpireCommand.build(redisClient, decodeMessageToString(messages[1]), Long.parseLong(decodeMessageToString(messages[2])));
                case CommandType.TTL:
                    return TTLCommand.build(redisClient, decodeMessageToString(messages[1]));
                case CommandType.PTTL:
                    return PTTLCommand.build(redisClient, decodeMessageToString(messages[1]));
                default:
                    ExceptionThrower.UNKNOWN_COMMAND.throwException();
            }
        } catch (Exception ex) {
            if (ex instanceof RedisException) {
                throw ex;
            }
            ExceptionThrower.ERROR_PARAM.throwException(commandTag);
        }
        return null;
    }


    private static SetCommand tryBuildSetCommand(RedisClient redisClient, FullBulkStringRedisMessage[] messages) {
        if (messages.length == 3) {
            return SetCommand.build(redisClient, decodeMessageToString(messages[1]), decodeMessageToString(messages[2]), null);
        }
        if (messages.length == 5 && "ex".equalsIgnoreCase(decodeMessageToString(messages[3]))) {
            return SetCommand.build(redisClient, decodeMessageToString(messages[1]), decodeMessageToString(messages[2]), Long.parseLong(decodeMessageToString(messages[4])));
        }
        ExceptionThrower.ERROR_PARAM.throwException("set");
        return null;
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


}
