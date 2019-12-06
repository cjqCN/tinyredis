package com.github.cjqcn.tinyredis.core.struct

class StringObject(val content: String) : RedisObject {
    companion object {
        val NULL = StringObject("null")
    }

    override fun type(): RedisObjectType {
        return RedisObjectType.string;
    }

    override fun hashCode(): Int {
        return content.hashCode()
    }

    override fun equals(other: Any?): Boolean {
        if (other !is StringObject) {
            return false
        }
        return content.equals(other.content)
    }

}