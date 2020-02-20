package com.github.cjqcn.tinyredis.core.command.impl;

import com.github.cjqcn.tinyredis.core.client.RedisClient;
import com.github.cjqcn.tinyredis.core.command.RedisCommand;
import com.github.cjqcn.tinyredis.core.struct.RedisDb;
import com.github.cjqcn.tinyredis.core.util.DBUtil;

import java.util.Arrays;

public class DelCommand extends AbstractCommand implements RedisCommand {
    private final String[] keys;

    public DelCommand(RedisClient redisClient, String... keys) {
        super(redisClient);
        this.keys = keys;
    }

    @Override
    public void execute() {
        RedisDb redisDb = redisClient.curDb();
        int res = 0;
        for (String key : keys) {
            if (DBUtil.delKey(redisDb, key) != null) {
                res++;
            }
        }
        redisClient.stream().responseString(String.valueOf(res));
    }

    @Override
    public String decode() {
        return "del " + Arrays.toString(keys);
    }


    public static DelCommand build(RedisClient redisClient, String... keys) {
        return new DelCommand(redisClient, keys);
    }
}
