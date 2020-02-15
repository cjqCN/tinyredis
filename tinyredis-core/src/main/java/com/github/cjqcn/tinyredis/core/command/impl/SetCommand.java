package com.github.cjqcn.tinyredis.core.command.impl;

import com.github.cjqcn.tinyredis.core.client.RedisClient;
import com.github.cjqcn.tinyredis.core.command.RedisCommand;
import com.github.cjqcn.tinyredis.core.db.RedisDb;
import com.github.cjqcn.tinyredis.core.struct.impl.StringRedisObject;
import com.github.cjqcn.tinyredis.core.util.TimeUtil;

public class SetCommand extends AbstractCommand implements RedisCommand {

    private String key;
    private String value;
    private long ttl;

    public SetCommand(RedisClient redisClient, String key, String value, long ttl) {
        super(redisClient);
        this.key = key;
        this.value = value;
        this.ttl = ttl;
    }

    @Override
    public void execute() {
        RedisDb db = redisClient.curDb();
        db.dict().set(key, StringRedisObject.valueOf(value));
        if (ttl > 0) {
            db.expires().set(key, ttl);
        }
        redisClient.stream().response(SimpleStringResponse.OK);
    }


    public static SetCommand build(RedisClient redisClient, String key, String value, long expire) {
        return new SetCommand(redisClient, key, value, expire * 1000 + TimeUtil.currentTimeMillis());
    }
}
