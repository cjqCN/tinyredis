package com.github.cjqcn.tinyredis.core.command.impl

import com.github.cjqcn.tinyredis.core.command.RedisResponse
import com.github.cjqcn.tinyredis.core.struct.StringObject

class GetResponse(val obj: StringObject) : RedisResponse {

    override fun decode(): String {
        return obj.content;
    }
}