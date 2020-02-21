package com.github.cjqcn.tinyredis.core.command.impl.string;

import com.github.cjqcn.tinyredis.core.client.RedisClient;
import com.github.cjqcn.tinyredis.core.command.RedisCommand;
import com.github.cjqcn.tinyredis.core.command.impl.AbstractCommand;
import com.github.cjqcn.tinyredis.core.command.impl.SimpleStringResponse;
import com.github.cjqcn.tinyredis.core.exception.ExceptionThrower;
import com.github.cjqcn.tinyredis.core.struct.RedisDb;
import com.github.cjqcn.tinyredis.core.struct.RedisObject;
import com.github.cjqcn.tinyredis.core.struct.impl.StringRedisObject;
import com.github.cjqcn.tinyredis.core.util.DBUtil;

public class GetCommand extends AbstractCommand implements RedisCommand {
    private String key;

    public GetCommand(RedisClient redisClient, String key) {
        super(redisClient);
        this.key = key;
    }

    @Override
    public void execute0() {
        execute0(redisClient, key);
    }

    @Override
    public String decode() {
        return "get " + key;
    }

    public void execute0(RedisClient redisClient, String key) {
        RedisDb db = redisClient.curDb();
        RedisObject value = DBUtil.lookupKeyRead(db, key);
        if (value == null) {
            redisClient.stream().response(SimpleStringResponse.NULL);
            return;
        }
        if (value instanceof StringRedisObject) {
            redisClient.stream().responseString(value.get().toString());
        } else {
            ExceptionThrower.WRONG_TYPE_OPERATION.throwException();
        }
    }

    public static GetCommand build(RedisClient redisClient, String value) {
        // TODO cache
        return new GetCommand(redisClient, value);
    }

    @Override
    public String toString() {
        return "GetCommand{" + key + '}';
    }
}
