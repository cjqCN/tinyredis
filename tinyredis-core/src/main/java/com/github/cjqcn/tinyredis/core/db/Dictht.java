package com.github.cjqcn.tinyredis.core.db;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 词典 hashtable
 */
@Setter
@Getter
@ToString
public class Dictht {

    /**
     * 哈希表
     */
    private DictEntry[] table;

    /**
     * 表大小
     */
    private long size;

    /**
     * 掩码
     */
    private long sizemask;

    /**
     * 使用节点数
     */
    private long userd;


}
