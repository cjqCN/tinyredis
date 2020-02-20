package com.github.cjqcn.tinyredis.core.command.impl;

import com.github.cjqcn.tinyredis.core.client.RedisClient;
import com.github.cjqcn.tinyredis.core.command.RedisCommand;
import com.github.cjqcn.tinyredis.core.struct.RedisDb;
import com.github.cjqcn.tinyredis.core.struct.RedisObject;
import com.github.cjqcn.tinyredis.core.util.DBUtil;
import com.github.cjqcn.tinyredis.core.util.TimeUtil;

public class TTLCommand extends AbstractCommand implements RedisCommand {
    private final String key;

    public TTLCommand(RedisClient redisClient, String key) {
        super(redisClient);
        this.key = key;
    }

    @Override
    public void execute() {
        execute0(redisClient, key);
    }

    @Override
    public String decode() {
        return "ttl " + key;
    }

    public void execute0(RedisClient redisClient, String key) {
        RedisDb db = redisClient.curDb();
        RedisObject value = DBUtil.lookupKeyRead(db, key);
        if (value == null) {
            redisClient.stream().responseString("-2");
            return;
        }
        Long expireTimestamp = db.expires().get(key);
        if (expireTimestamp == null) {
            redisClient.stream().responseString("-1");
        } else {
            redisClient.stream().responseString(String.valueOf((expireTimestamp - TimeUtil.currentTimeMillis()) / 1000L));
        }
    }

    public static TTLCommand build(RedisClient redisClient, String value) {
        return new TTLCommand(redisClient, value);
    }


}