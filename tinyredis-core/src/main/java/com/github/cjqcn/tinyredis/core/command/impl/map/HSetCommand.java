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

public class HSetCommand extends AbstractCommand implements RedisCommand {

    private String key;
    private String field;
    private String value;

    protected HSetCommand(RedisClient redisClient, String key, String field, String value) {
        super(redisClient);
        this.key = key;
        this.field = field;
        this.value = value;
    }

    @Override
    public void execute0() {
        RedisDb db = redisClient.curDb();
        BaseDict<String, StringRedisObject> baseDict = getAndInitHMap(db, key);
        baseDict.set(field, StringRedisObject.valueOf(value));
        redisClient.stream().response(SimpleStringResponse.OK);
    }

    private BaseDict<String, StringRedisObject> getAndInitHMap(RedisDb db, String key) {
        RedisObject hMap = DBUtil.lookupKeyRead(db, key);
        if (hMap == null) {
            synchronized (db) {
                hMap = db.dict().get(key);
                if (hMap == null) {
                    hMap = new BaseDict();
                    db.dict().set(key, hMap);
                }
            }
        }
        if (!(hMap instanceof BaseDict)) {
            ExceptionThrower.WRONG_TYPE_OPERATION.throwException();
        }
        return (BaseDict<String, StringRedisObject>) hMap;
    }


    @Override
    public String decode() {
        return "hset " + key + " " + field + " " + value;
    }


    public static HSetCommand build(RedisClient redisClient, String key, String field, String value) {
        return new HSetCommand(redisClient, key, field, value);
    }

}
