package com.github.cjqcn.tinyredis.core.command.impl

import com.github.cjqcn.tinyredis.core.command.RedisResponse

class ErrorResponse(val msg: String) : RedisResponse {

    override fun decode(): String {
        return msg;
    }

}