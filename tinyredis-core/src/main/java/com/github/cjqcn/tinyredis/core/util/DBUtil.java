package com.github.cjqcn.tinyredis.core.util;

import com.github.cjqcn.tinyredis.core.exception.ExceptionThrower;
import com.github.cjqcn.tinyredis.core.struct.RedisDb;
import com.github.cjqcn.tinyredis.core.struct.RedisObject;
import com.github.cjqcn.tinyredis.core.struct.impl.StringRedisObject;

public final class DBUtil {
    private DBUtil() {
    }

    public static RedisObject lookupKeyRead(RedisDb db, String key) {
        if (expireIfNeeded(db, key)) {
            return null;
        }
        return db.dict().get(key);
    }


    public static StringRedisObject lookupKeyStringRead(RedisDb db, String key) {
        if (expireIfNeeded(db, key)) {
            return null;
        }
        RedisObject object = db.dict().get(key);
        if (object == null) {
            return null;
        }
        if (!(object instanceof StringRedisObject)) {
            ExceptionThrower.WRONG_TYPE_OPERATION.throwException();
        }
        return (StringRedisObject) object;
    }

    public static RedisObject delKey(RedisDb db, String key) {
        if (expireIfNeeded(db, key)) {
            return null;
        }
        RedisObject o = db.dict().remove(key);
        if (o != null) {
            db.expires().remove(key);
        }
        return o;
    }

    private static final boolean expireIfNeeded(RedisDb db, String key) {
        Long ttl = db.expires().get(key);
        if (!isLive(ttl)) {
            db.expires().remove(key);
            db.dict().remove(key);
            return true;
        }
        return false;
    }


    private static boolean isLive(Long ttl) {
        return ttl == null || ttl.longValue() > TimeUtil.currentTimeMillis();
    }
}
