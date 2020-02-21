package com.github.cjqcn.tinyredis.core.command.impl.string;

import com.github.cjqcn.tinyredis.core.client.RedisClient;
import com.github.cjqcn.tinyredis.core.command.RedisCommand;
import com.github.cjqcn.tinyredis.core.command.impl.AbstractCommand;
import com.github.cjqcn.tinyredis.core.struct.RedisDb;
import com.github.cjqcn.tinyredis.core.struct.RedisObject;
import com.github.cjqcn.tinyredis.core.util.DBUtil;
import com.github.cjqcn.tinyredis.core.util.TimeUtil;

public class ExpireCommand extends AbstractCommand implements RedisCommand {

    private final String key;
    private final long expireSec;

    public ExpireCommand(RedisClient redisClient, String key, long expireSec) {
        super(redisClient);
        this.key = key;
        this.expireSec = expireSec;
    }

    @Override
    public void execute0() {
        execute0(redisClient, key, expireSec);
    }

    @Override
    public String decode() {
        return "expire " + key + " " + expireSec;
    }

    public void execute0(RedisClient redisClient, String key, long expireSec) {
        RedisDb db = redisClient.curDb();
        RedisObject value = DBUtil.lookupKeyRead(db, key);
        if (value == null) {
            redisClient.stream().responseString("0");
        } else {
            db.expires().set(key, TimeUtil.nextSecTimeMillis(expireSec));
            redisClient.stream().responseString("1");
        }
    }


    public static ExpireCommand build(RedisClient redisClient, String key, long expire) {
        return new ExpireCommand(redisClient, key, expire);
    }


}
