package com.github.cjqcn.tinyredis.core.command.impl.string;

import com.github.cjqcn.tinyredis.core.client.RedisClient;
import com.github.cjqcn.tinyredis.core.command.RedisCommand;
import com.github.cjqcn.tinyredis.core.command.impl.AbstractCommand;
import com.github.cjqcn.tinyredis.core.exception.ExceptionThrower;
import com.github.cjqcn.tinyredis.core.struct.RedisDb;
import com.github.cjqcn.tinyredis.core.struct.RedisObject;
import com.github.cjqcn.tinyredis.core.struct.impl.StringRedisObject;
import com.github.cjqcn.tinyredis.core.util.DBUtil;

public class StrLenCommand extends AbstractCommand implements RedisCommand {
    private String key;

    protected StrLenCommand(RedisClient redisClient, final String key) {
        super(redisClient);
        this.key = key;
    }

    @Override
    protected void execute0() {
        RedisDb db = redisClient.curDb();
        RedisObject value = DBUtil.lookupKeyRead(db, key);
        if (value == null) {
            redisClient.stream().responseString("-1");
            return;
        }
        if (value instanceof StringRedisObject) {
            redisClient.stream().response(((StringRedisObject) value).content().length());
        } else {
            ExceptionThrower.WRONG_TYPE_OPERATION.throwException();
        }
    }

    @Override
    public String decode() {
        return "strlen " + key;
    }

    public static StrLenCommand build(RedisClient redisClient, String value) {
        return new StrLenCommand(redisClient, value);
    }
}
