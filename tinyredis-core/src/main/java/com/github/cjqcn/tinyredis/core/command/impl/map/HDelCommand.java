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

public class HDelCommand extends AbstractCommand implements RedisCommand {

    private String key;
    private String field;

    protected HDelCommand(RedisClient redisClient, String key, String field) {
        super(redisClient);
        this.key = key;
        this.field = field;
    }

    @Override
    public void execute0() {
        RedisDb db = redisClient.curDb();
        RedisObject hMap = db.dict().get(key);
        if (hMap == null) {
            redisClient.stream().response(SimpleStringResponse._0);
            return;
        }
        if (!(hMap instanceof BaseDict)) {
            ExceptionThrower.WRONG_TYPE_OPERATION.throwException();
        } else {
            BaseDict<String, StringRedisObject> dict = (BaseDict) hMap;
            StringRedisObject value = dict.remove(field);
            if (value == null) {
                redisClient.stream().response(SimpleStringResponse._0);
            } else {
                redisClient.stream().response(SimpleStringResponse._1);
            }
            if (dict.size() == 0) {
                db.dict().remove(key);
            }
        }
    }


    public static HDelCommand build(RedisClient redisClient, String key, String field) {
        return new HDelCommand(redisClient, key, field);
    }

    @Override
    public String decode() {
        return "hdel " + key + " " + field;
    }
}
