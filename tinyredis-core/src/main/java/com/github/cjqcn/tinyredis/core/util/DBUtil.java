package com.github.cjqcn.tinyredis.core.util;

import com.github.cjqcn.tinyredis.core.db.RedisDb;
import com.github.cjqcn.tinyredis.core.struct.RedisObject;

public final class DBUtil {
    private DBUtil() {
    }

    public static RedisObject getValueWithExpire(RedisDb db, String key) {
        Long ttl = db.expires().get(key);
        if (!isLive(ttl)) {
            db.expires().remove(key);
            db.dict().remove(key);
            return null;
        }
        return db.dict().get(key);
    }


    private static boolean isLive(Long ttl) {
        return ttl != null && ttl.longValue() > TimeUtil.currentTimeMillis();
    }
}
