package com.github.cjqcn.tinyredis.core.command.impl.string;

import com.github.cjqcn.tinyredis.core.client.RedisClient;
import com.github.cjqcn.tinyredis.core.command.RedisCommand;
import com.github.cjqcn.tinyredis.core.command.impl.AbstractCommand;
import com.github.cjqcn.tinyredis.core.struct.RedisDb;
import com.github.cjqcn.tinyredis.core.struct.RedisObject;
import com.github.cjqcn.tinyredis.core.struct.impl.StringRedisObject;

public class SetNxCommand extends AbstractCommand implements RedisCommand {

    private String key;
    private String value;

    public SetNxCommand(RedisClient redisClient, String key, String value) {
        super(redisClient);
        this.key = key;
        this.value = value;
    }

    @Override
    public void execute0() {
        RedisDb db = redisClient.curDb();
        RedisObject newValue = StringRedisObject.valueOf(value);
        RedisObject oldValue = db.dict().setnx(key, newValue);
        if (oldValue == null) {
            redisClient.stream().responseString("1");
        } else {
            redisClient.stream().responseString("0");
        }
    }

    @Override
    public String decode() {
        return String.format("setnx %s %s", key, value);
    }


    public static SetNxCommand build(RedisClient redisClient, String key, String value) {
        return new SetNxCommand(redisClient, key, value);
    }
}
