package com.github.cjqcn.tinyredis.core.command.impl.string;

import com.github.cjqcn.tinyredis.core.client.RedisClient;
import com.github.cjqcn.tinyredis.core.command.RedisCommand;
import com.github.cjqcn.tinyredis.core.command.impl.AbstractCommand;
import com.github.cjqcn.tinyredis.core.command.impl.SimpleStringResponse;
import com.github.cjqcn.tinyredis.core.exception.ExceptionThrower;
import com.github.cjqcn.tinyredis.core.struct.RedisDb;
import com.github.cjqcn.tinyredis.core.struct.impl.StringRedisObject;
import com.github.cjqcn.tinyredis.core.util.TimeUtil;

public class SetExCommand extends AbstractCommand implements RedisCommand {

    private String key;
    private String value;
    private long expireSec;

    public SetExCommand(RedisClient redisClient, String key, String value, long expireSec) {
        super(redisClient);
        this.key = key;
        this.value = value;
        this.expireSec = expireSec;
    }

    @Override
    public void execute0() {
        if (expireSec <= 0) {
            ExceptionThrower.INVALID_EXPIRE_TIME.throwException("setex");
        }
        RedisDb db = redisClient.curDb();
        db.dict().set(key, StringRedisObject.valueOf(value));
        db.expires().set(key, TimeUtil.nextSecTimeMillis(expireSec));
        redisClient.stream().response(SimpleStringResponse.OK);
    }

    @Override
    public String decode() {
        return String.format("setex %s %d %s", key, expireSec, value);
    }


    public static SetCommand build(RedisClient redisClient, String key, String value, long expireSec) {
        return new SetCommand(redisClient, key, value, expireSec);
    }
}
