package com.github.cjqcn.tinyredis.core.db;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * redis db
 */
@Getter
@Setter
@ToString
public class RedisDb {

    /**
     * 数据词典
     */
    private Dict dict;

    /**
     * 键过期词典
     */
    private Dict expires;

    /**
     * 阻塞 key 词典
     */
    private Dict blockingKeys;

    /**
     * 已准备 key 词典
     */
    private Dict readyKeys;

    /**
     * 被 watch 监控 key 词典
     */
    private Dict watchedKeys;

    /**
     * key 的驱逐池
     */
    private EvictionPoolEntry evictionPool;

    /**
     * 序号
     */
    private int id;

    /**
     * 数据库键平均 ttl， 仅用于统计
     */
    private long avgTtl;

}
