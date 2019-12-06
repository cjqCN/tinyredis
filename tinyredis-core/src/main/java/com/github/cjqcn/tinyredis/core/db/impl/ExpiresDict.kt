package com.github.cjqcn.tinyredis.core.db.impl

import com.github.cjqcn.tinyredis.core.struct.RedisObject
import com.github.cjqcn.tinyredis.core.struct.TtlObject
import java.util.concurrent.atomic.AtomicLong

class ExpiresDict : AbstractDict() {

    val ttlCounter = AtomicLong(0);

    override fun get(key: RedisObject): RedisObject? {
        return map[key];
    }

    override fun set(key: RedisObject, value: RedisObject): Boolean {
        if (value !is TtlObject) {
            return false
        }
        ttlCounter.addAndGet(value.ttl)
        map[key] = value
        return true
    }

    override fun del(key: RedisObject): Boolean {
        return map.remove(key) != null;
    }


    fun avgTtl(): Long {
        if (map.isEmpty()) {
            return 0;
        }
        return ttlCounter.get() / map.size;
    }
}