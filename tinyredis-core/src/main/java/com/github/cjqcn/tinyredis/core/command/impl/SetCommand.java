package com.github.cjqcn.tinyredis.core.command.impl;

import com.github.cjqcn.tinyredis.core.client.RedisClient;
import com.github.cjqcn.tinyredis.core.command.RedisCommand;
import com.github.cjqcn.tinyredis.core.db.RedisDb;
import com.github.cjqcn.tinyredis.core.exception.ExceptionThrower;
import com.github.cjqcn.tinyredis.core.struct.impl.StringRedisObject;
import com.github.cjqcn.tinyredis.core.util.TimeUtil;

public class SetCommand extends AbstractCommand implements RedisCommand {

    private String key;
    private String value;
    private Long expireSec;

    public SetCommand(RedisClient redisClient, String key, String value, Long expireSec) {
        super(redisClient);
        this.key = key;
        this.value = value;
        this.expireSec = expireSec;
    }

    @Override
    public void execute() {
        if (expireSec != null && expireSec <= 0) {
            ExceptionThrower.INVALID_EXPIRE_TIME.throwException("set");
        }
        RedisDb db = redisClient.curDb();
        db.dict().set(key, StringRedisObject.valueOf(value));
        if (expireSec != null) {
            db.expires().set(key, TimeUtil.nextSecTimeMillis(expireSec));
        }
        redisClient.stream().response(SimpleStringResponse.OK);
    }

    @Override
    public String decode() {
        if (expireSec != null) {
            return String.format("set %s %s %d", key, value, expireSec);
        }
        return String.format("set %s %s", key, value);
    }


    public static SetCommand build(RedisClient redisClient, String key, String value, Long expireSec) {
        return new SetCommand(redisClient, key, value, expireSec);
    }
}
