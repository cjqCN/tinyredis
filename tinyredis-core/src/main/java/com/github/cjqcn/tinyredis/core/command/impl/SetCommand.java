package com.github.cjqcn.tinyredis.core.command.impl;

import com.github.cjqcn.tinyredis.core.client.RedisClient;
import com.github.cjqcn.tinyredis.core.command.RedisCommand;
import com.github.cjqcn.tinyredis.core.db.RedisDb;
import com.github.cjqcn.tinyredis.core.struct.impl.StringRedisObject;
import com.github.cjqcn.tinyredis.core.util.TimeUtil;

public class SetCommand extends AbstractCommand implements RedisCommand {

    private String key;
    private String value;
    private long expireSec;

    public SetCommand(RedisClient redisClient, String key, String value, long expireSec) {
        super(redisClient);
        this.key = key;
        this.value = value;
        this.expireSec = expireSec;
    }

    @Override
    public void execute() {
        RedisDb db = redisClient.curDb();
        db.dict().set(key, StringRedisObject.valueOf(value));
        if (expireSec > 0) {
            db.expires().set(key, TimeUtil.nextSecTimeMillis(expireSec));
        }
        redisClient.stream().response(SimpleStringResponse.OK);
    }

    @Override
    public String decode() {
        return String.format("set %s %s %d", key, value, expireSec);
    }


    public static SetCommand build(RedisClient redisClient, String key, String value, long expireSec) {
        return new SetCommand(redisClient, key, value, expireSec);
    }
}
