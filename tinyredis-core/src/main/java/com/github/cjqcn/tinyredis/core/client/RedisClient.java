package com.github.cjqcn.tinyredis.core.client;

import com.github.cjqcn.tinyredis.core.command.RedisCommand;
import com.github.cjqcn.tinyredis.core.db.RedisDb;
import com.github.cjqcn.tinyredis.core.struct.RedisObject;
import com.github.cjqcn.tinyredis.core.struct.Sds;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * redis 客户端信息
 */
@Setter
@Getter
@ToString
public class RedisClient {

    /**
     * 套接字 fd
     */
    private int fd;

    /**
     * 正在使用的 redisDb
     */
    private RedisDb db;

    /**
     * 词典id
     */
    private int dictid;

    /**
     * 客户端name
     */
    private RedisObject name;

    /**
     * 查询缓存区
     */
    private Sds querybuf;

    /**
     * 最近 100ms+ 查询缓存区的峰值
     */
    private long querybufPeak;

    /**
     * 参数数量
     */
    private int argc;

    /**
     * 参数数组
     */
    private RedisObject[] argv;

    /**
     * 执行的指令
     */
    private RedisCommand cmd, lastcmd;

    /**
     * 请求的类型
     */
    private int reqtype;


}
