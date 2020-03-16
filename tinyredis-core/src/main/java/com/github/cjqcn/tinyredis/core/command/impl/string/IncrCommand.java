package com.github.cjqcn.tinyredis.core.command.impl.string;

import com.github.cjqcn.tinyredis.core.client.RedisClient;
import com.github.cjqcn.tinyredis.core.command.RedisCommand;
import com.github.cjqcn.tinyredis.core.command.impl.AbstractCommand;
import com.github.cjqcn.tinyredis.core.struct.RedisDb;
import com.github.cjqcn.tinyredis.core.struct.impl.Sds;
import com.github.cjqcn.tinyredis.core.util.DBUtil;

import static com.github.cjqcn.tinyredis.core.exception.ExceptionThrower.NOT_INTEGER_OR_OUT_OF_RANGE;

public class IncrCommand extends AbstractCommand implements RedisCommand {

    private String key;

    protected IncrCommand(RedisClient redisClient, String key) {
        super(redisClient);
        this.key = key;
    }

    @Override
    protected void execute0() {
        RedisDb db = redisClient.curDb();
        Sds value = DBUtil.lookupKeyStringRead(db, key);
        if (value == null) {
            db.dict().set(key, Sds.valueOf(1));
            redisClient.stream().response(1);
            return;
        }
        String content = value.content();
        long res = 0;
        try {
            res = Long.parseLong(content);
        } catch (NumberFormatException ex) {
            NOT_INTEGER_OR_OUT_OF_RANGE.throwException();
        }
        if (res == Long.MAX_VALUE) {
            NOT_INTEGER_OR_OUT_OF_RANGE.throwException();
        }
        db.dict().set(key, Sds.valueOf(++res));
        redisClient.stream().response(res);
    }

    @Override
    public String decode() {
        return "incr " + key;
    }

    public static IncrCommand build(RedisClient redisClient, String key) {
        return new IncrCommand(redisClient, key);
    }
}
