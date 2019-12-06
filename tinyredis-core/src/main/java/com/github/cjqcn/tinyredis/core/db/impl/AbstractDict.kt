package com.github.cjqcn.tinyredis.core.db.impl

import com.github.cjqcn.tinyredis.core.db.Dict
import com.github.cjqcn.tinyredis.core.struct.RedisObject

abstract class AbstractDict : Dict {
    val map = HashMap<RedisObject, RedisObject>()
}