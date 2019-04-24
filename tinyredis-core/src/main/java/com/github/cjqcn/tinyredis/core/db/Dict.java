package com.github.cjqcn.tinyredis.core.db;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 词典
 */
@Getter
@Setter
@ToString
public class Dict {

    /**
     * 词典类型-函数
     */
    private DictType dictType;

    /**
     * 私有类型
     */
    private Object privadata;

    /**
     * 哈希表数组
     * ht[0] 客户端操作
     * ht[1] rehash
     */
    private Dictht[] ht = new Dictht[2];

    /**
     * rehash 索引
     * 不进行 rehash 时，值为 -1
     */
    private int rehashidx;

    /**
     * 当前运行的迭代器数量
     */
    private int iterators;


}
