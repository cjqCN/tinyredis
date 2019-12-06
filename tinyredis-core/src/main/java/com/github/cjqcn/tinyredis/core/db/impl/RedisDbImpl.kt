package com.github.cjqcn.tinyredis.core.db.impl

import com.github.cjqcn.tinyredis.core.db.Dict
import com.github.cjqcn.tinyredis.core.db.RedisDb

class RedisDbImpl : RedisDb {

    val dict = BaseDict();
    val expires = ExpiresDict();

    override fun id(): Int {
        return 1
    }

    override fun dict(): Dict {
        return dict
    }

    override fun expires(): Dict {
        return expires
    }

    override fun avgTtl(): Long {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}