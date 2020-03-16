package com.github.cjqcn.tinyredis.core.command.impl.string;

import com.github.cjqcn.tinyredis.core.client.RedisClient;
import com.github.cjqcn.tinyredis.core.command.RedisCommand;
import com.github.cjqcn.tinyredis.core.command.RedisResponse;
import com.github.cjqcn.tinyredis.core.command.impl.AbstractCommand;
import com.github.cjqcn.tinyredis.core.command.impl.ArrayResponse;
import com.github.cjqcn.tinyredis.core.command.impl.SimpleStringResponse;
import com.github.cjqcn.tinyredis.core.struct.RedisDb;
import com.github.cjqcn.tinyredis.core.struct.RedisObject;
import com.github.cjqcn.tinyredis.core.struct.impl.Sds;
import com.github.cjqcn.tinyredis.core.util.DBUtil;

import java.util.ArrayList;
import java.util.List;

public class MGetCommand extends AbstractCommand implements RedisCommand {

    private String[] keys;

    protected MGetCommand(RedisClient redisClient, String[] keys) {
        super(redisClient);
        this.keys = keys;
    }

    @Override
    protected void execute0() {
        RedisDb redisDb = redisClient.curDb();
        List<RedisResponse> redisResponses = new ArrayList<>(keys.length);
        for (int i = 1; i < keys.length; i++) {
            RedisObject value = DBUtil.lookupKeyRead(redisDb, keys[i]);
            if (value == null || !(value instanceof Sds)) {
                redisResponses.add(SimpleStringResponse.NULL);
            } else {
                redisResponses.add(SimpleStringResponse.valueOf(((Sds) value).get()));
            }
        }
        redisClient.stream().response(new ArrayResponse(redisResponses));
    }


    public static MGetCommand build(RedisClient redisClient, String[] keys) {
        return new MGetCommand(redisClient, keys);
    }


    @Override
    public String decode() {
        StringBuffer sb = new StringBuffer("mget");
        for (int i = 1; i < keys.length; i++) {
            sb.append(" ").append(keys[i]);
        }
        return sb.toString();
    }
}
