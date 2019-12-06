package com.github.cjqcn.tinyredis.core.db

interface RedisDb {
    fun id(): Int
    fun dict(): Dict
    fun expires(): Dict
    fun avgTtl(): Long
}