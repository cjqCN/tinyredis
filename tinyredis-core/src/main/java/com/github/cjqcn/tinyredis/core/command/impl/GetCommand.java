package com.github.cjqcn.tinyredis.core.command.impl;

import com.github.cjqcn.tinyredis.core.client.RedisClient;
import com.github.cjqcn.tinyredis.core.command.CommandParam;
import com.github.cjqcn.tinyredis.core.command.CommandType;
import com.github.cjqcn.tinyredis.core.command.RedisCommand;
import com.github.cjqcn.tinyredis.core.db.RedisDb;
import com.github.cjqcn.tinyredis.core.struct.RedisObject;
import com.github.cjqcn.tinyredis.core.struct.impl.StringRedisObject;
import com.github.cjqcn.tinyredis.core.struct.impl.TtlRedisObject;

public class GetCommand extends AbstractCommand implements RedisCommand {

    @Override
    public void execute0(RedisClient redisClient, CommandParam... params) {
        RedisDb db = redisClient.curDb();
        StringRedisObject key = StringRedisObject.valueOf(params[0].getAsString());
        TtlRedisObject ttl = (TtlRedisObject) db.expires().get(key);
        if (ttl != null && !ttl.isLive()) {
            db.expires().remove(key);
            db.dict().remove(key);
            redisClient.stream().response(SimpleStringResponse.GET_NULL);
        }
        RedisObject value = (RedisObject) db.dict().get(key);
        if (value == null) {
            redisClient.stream().response(SimpleStringResponse.GET_NULL);
        }
        if (value instanceof StringRedisObject) {
            redisClient.stream().response(new SimpleStringResponse(((StringRedisObject) value).content()));
        } else {
            redisClient.stream().error("WRONGTYPE Operation against a key holding the wrong kind of value");
        }
    }

    @Override
    public CommandType type() {
        return CommandType.get;
    }
}
