package com.github.cjqcn.tinyredis.core.command.impl;

import com.github.cjqcn.tinyredis.core.client.RedisClient;
import com.github.cjqcn.tinyredis.core.command.RedisCommand;
import com.github.cjqcn.tinyredis.core.db.RedisDb;
import com.github.cjqcn.tinyredis.core.struct.impl.StringRedisObject;
import com.github.cjqcn.tinyredis.core.util.TimeUtil;

public class SetCommand extends AbstractCommand implements RedisCommand {

    private String key;
    private String value;
    private long expire;

    public SetCommand(RedisClient redisClient, String key, String value, long expire) {
        super(redisClient);
        this.key = key;
        this.value = value;
        this.expire = expire;
    }

    @Override
    public void execute() {
        RedisDb db = redisClient.curDb();
        db.dict().set(key, StringRedisObject.valueOf(value));
        if (expire > 0) {
            db.expires().set(key, expire * 1000 + TimeUtil.currentTimeMillis());
        }
        redisClient.stream().response(SimpleStringResponse.OK);
    }

    @Override
    public String decode() {
        return String.format("set %s %s %d", key, value, expire);
    }


    public static SetCommand build(RedisClient redisClient, String key, String value, long expire) {
        return new SetCommand(redisClient, key, value, expire);
    }
}
