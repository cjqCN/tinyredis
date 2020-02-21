package com.github.cjqcn.tinyredis.core.command.impl.string;

import com.github.cjqcn.tinyredis.core.client.RedisClient;
import com.github.cjqcn.tinyredis.core.command.RedisCommand;
import com.github.cjqcn.tinyredis.core.command.impl.AbstractCommand;
import com.github.cjqcn.tinyredis.core.command.impl.SimpleStringResponse;
import com.github.cjqcn.tinyredis.core.exception.ExceptionThrower;
import com.github.cjqcn.tinyredis.core.struct.RedisDb;
import com.github.cjqcn.tinyredis.core.struct.impl.StringRedisObject;
import com.github.cjqcn.tinyredis.core.util.TimeUtil;

public class PSetExCommand extends AbstractCommand implements RedisCommand {

    private String key;
    private String value;
    private long expireMills;

    public PSetExCommand(RedisClient redisClient, String key, String value, long expireMills) {
        super(redisClient);
        this.key = key;
        this.value = value;
        this.expireMills = expireMills;
    }

    @Override
    public void execute0() {
        if (expireMills <= 0) {
            ExceptionThrower.INVALID_EXPIRE_TIME.throwException("psetex");
        }
        RedisDb db = redisClient.curDb();
        db.dict().set(key, StringRedisObject.valueOf(value));
        db.expires().set(key, TimeUtil.nextMillisTimeMillis(expireMills));
        redisClient.stream().response(SimpleStringResponse.OK);
    }

    @Override
    public String decode() {
        return String.format("psetex %s %d %s", key, expireMills, value);
    }


    public static PSetExCommand build(RedisClient redisClient, String key, String value, long expireMills) {
        return new PSetExCommand(redisClient, key, value, expireMills);
    }
}
