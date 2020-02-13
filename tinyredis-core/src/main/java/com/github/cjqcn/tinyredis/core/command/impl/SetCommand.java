package com.github.cjqcn.tinyredis.core.command.impl;

import com.github.cjqcn.tinyredis.core.client.RedisClient;
import com.github.cjqcn.tinyredis.core.command.CommandParam;
import com.github.cjqcn.tinyredis.core.command.CommandType;
import com.github.cjqcn.tinyredis.core.command.RedisCommand;
import com.github.cjqcn.tinyredis.core.db.RedisDb;
import com.github.cjqcn.tinyredis.core.struct.impl.StringRedisObject;
import com.github.cjqcn.tinyredis.core.struct.impl.TtlRedisObject;

public class SetCommand extends AbstractCommand implements RedisCommand {

    @Override
    public void execute0(RedisClient client, CommandParam... params) {
        RedisDb db = client.curDb();
        StringRedisObject key = StringRedisObject.valueOf(params[0].getAsString());
        StringRedisObject value = StringRedisObject.valueOf(params[1].getAsString());
        TtlRedisObject ttl = TtlRedisObject.valueOf(params[2].getAsLong());
        db.dict().set(key, value);
        if (ttl != null) {
            db.expires().set(key, ttl);
        }
        client.stream().response(SimpleStringResponse.OK);
    }

    @Override
    public CommandType type() {
        return null;
    }
}
