package com.github.cjqcn.tinyredis.core.command.impl;

import com.github.cjqcn.tinyredis.core.client.RedisClient;
import com.github.cjqcn.tinyredis.core.command.RedisCommand;
import com.github.cjqcn.tinyredis.core.struct.RedisDb;
import com.github.cjqcn.tinyredis.core.struct.RedisObject;
import com.github.cjqcn.tinyredis.core.util.DBUtil;

public class TypeCommand extends AbstractCommand implements RedisCommand {
    private final String key;

    public TypeCommand(RedisClient redisClient, String key) {
        super(redisClient);
        this.key = key;
    }

    @Override
    public void execute0() {
        execute0(redisClient, key);
    }

    @Override
    public String decode() {
        return "type " + key;
    }

    public void execute0(RedisClient redisClient, String key) {
        RedisDb db = redisClient.curDb();
        RedisObject value = DBUtil.lookupKeyRead(db, key);
        if (value == null) {
            redisClient.stream().responseString("none");
            return;
        }
        redisClient.stream().responseString(value.type().name());
    }

    public static TypeCommand build(RedisClient redisClient, String value) {
        return new TypeCommand(redisClient, value);
    }

}