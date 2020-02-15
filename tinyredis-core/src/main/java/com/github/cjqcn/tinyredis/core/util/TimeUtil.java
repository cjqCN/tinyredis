package com.github.cjqcn.tinyredis.core.util;

public final class TimeUtil {

    private TimeUtil() {
    }

    public static long currentTimeSec() {
        return currentTimeMillis() / 1000L;
    }


    public static long currentTimeMillis() {
        return System.currentTimeMillis();
    }

}
