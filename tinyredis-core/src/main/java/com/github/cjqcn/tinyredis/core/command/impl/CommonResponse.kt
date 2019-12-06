package com.github.cjqcn.tinyredis.core.command.impl

import com.github.cjqcn.tinyredis.core.command.RedisResponse

class CommonResponse(val msg: String) : RedisResponse {
    companion object {
        val OK = CommonResponse("OK")
    }

    override fun decode(): String {
        return msg;
    }


}
