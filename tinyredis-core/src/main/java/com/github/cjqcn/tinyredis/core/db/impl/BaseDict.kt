package com.github.cjqcn.tinyredis.core.db.impl

import com.github.cjqcn.tinyredis.core.struct.RedisObject

class BaseDict : AbstractDict() {

    override fun get(key: RedisObject): RedisObject? {
        return map[key];
    }

    override fun set(key: RedisObject, value: RedisObject): Boolean {
        map[key] = value
        return true
    }

    override fun del(key: RedisObject): Boolean {
        return map.remove(key) != null;
    }
}
