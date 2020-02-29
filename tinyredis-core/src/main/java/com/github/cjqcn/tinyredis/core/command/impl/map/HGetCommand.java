package com.github.cjqcn.tinyredis.core.command.impl.map;

import com.github.cjqcn.tinyredis.core.client.RedisClient;
import com.github.cjqcn.tinyredis.core.command.RedisCommand;
import com.github.cjqcn.tinyredis.core.command.impl.AbstractCommand;
import com.github.cjqcn.tinyredis.core.command.impl.SimpleStringResponse;
import com.github.cjqcn.tinyredis.core.exception.ExceptionThrower;
import com.github.cjqcn.tinyredis.core.struct.RedisDb;
import com.github.cjqcn.tinyredis.core.struct.RedisObject;
import com.github.cjqcn.tinyredis.core.struct.impl.BaseDict;
import com.github.cjqcn.tinyredis.core.struct.impl.StringRedisObject;
import com.github.cjqcn.tinyredis.core.util.DBUtil;

public class HGetCommand extends AbstractCommand implements RedisCommand {

    private String key;
    private String field;

    protected HGetCommand(RedisClient redisClient, String key, String field) {
        super(redisClient);
        this.key = key;
        this.field = field;
    }

    @Override
    public void execute0() {
        RedisDb db = redisClient.curDb();
        RedisObject hMap = DBUtil.lookupKeyRead(db, key);
        if (hMap == null) {
            redisClient.stream().response(SimpleStringResponse.NULL);
            return;
        }
        if (!(hMap instanceof BaseDict)) {
            ExceptionThrower.WRONG_TYPE_OPERATION.throwException();
        }
        BaseDict<String, StringRedisObject> dict = (BaseDict) hMap;
        StringRedisObject value = dict.get(field);
        if (value == null) {
            redisClient.stream().response(SimpleStringResponse.NULL);
        } else {
            redisClient.stream().responseString(value.get());
        }
    }


    public static HGetCommand build(RedisClient redisClient, String key, String field) {
        return new HGetCommand(redisClient, key, field);
    }

    @Override
    public String decode() {
        return "hget " + key + " " + field;
    }
}
