package com.github.cjqcn.tinyredis.core.command.impl;

import com.github.cjqcn.tinyredis.core.client.RedisClient;
import com.github.cjqcn.tinyredis.core.command.RedisCommand;
import com.github.cjqcn.tinyredis.core.db.RedisDb;
import com.github.cjqcn.tinyredis.core.exception.RedisException;
import com.github.cjqcn.tinyredis.core.struct.RedisObject;
import com.github.cjqcn.tinyredis.core.struct.impl.StringRedisObject;
import com.github.cjqcn.tinyredis.core.util.DBUtil;

public class GetCommand extends AbstractCommand implements RedisCommand {

    private final String key;

    public GetCommand(RedisClient redisClient, String key) {
        super(redisClient);
        this.key = key;
    }

    @Override
    public void execute() {
        execute0(redisClient, key);
    }

    public void execute0(RedisClient redisClient, String key) {
        RedisDb db = redisClient.curDb();
        RedisObject value = DBUtil.getValueWithExpire(db, key);
        if (value == null) {
            redisClient.stream().response(SimpleStringResponse.GET_NULL);
            return;
        }
        if (value instanceof StringRedisObject) {
            redisClient.stream().responseString(value.get().toString());
        } else {
            throw RedisException.WRONG_TYPE_OPERATION;
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