package com.github.cjqcn.tinyredis.core.exception;

public class ExceptionThrower {

    public static final ExceptionThrower WRONG_TYPE_OPERATION = new ExceptionThrower("WRONGTYPE Operation against a key holding the wrong kind of value");

    public static final ExceptionThrower NO_AUTH = new ExceptionThrower("NOAUTH Authentication required");

    public static final ExceptionThrower UNKNOWN_COMMAND = new ExceptionThrower("ERR unknown command '%s'");

    public static final ExceptionThrower WRONG_NUM_PARAM = new ExceptionThrower("ERR wrong number of arguments for '%d' command");

    public static final ExceptionThrower ERROR_PARAM = new ExceptionThrower("ERR syntax error");

    public static final ExceptionThrower AUTH_ERROR = new ExceptionThrower("ERR invalid password");

    public static final ExceptionThrower INVALID_EXPIRE_TIME = new ExceptionThrower("ERR invalid expire time in %s");

    private final String format;

    public ExceptionThrower(String format) {
        this.format = format;
    }

    public void throwException() {
        throw new RedisException(format);
    }

    public void throwException(Object arg) {
        throw new RedisException(String.format(format, arg));
    }

    public void throwException(Object arg0, Object arg1) {
        throw new RedisException(String.format(format, arg0, arg1));
    }

    public void throwException(Object arg0, Object arg1, Object arg2) {
        throw new RedisException(String.format(format, arg0, arg1, arg2));
    }

    public void throwException(Object arg0, Object arg1, Object arg2, Object arg3) {
        throw new RedisException(String.format(format, arg0, arg1, arg2, arg3));
    }

    public void throwException(Object arg0, Object arg1, Object arg2, Object arg3, Object args4) {
        throw new RedisException(String.format(format, arg0, arg1, arg2, arg3, args4));
    }


}
