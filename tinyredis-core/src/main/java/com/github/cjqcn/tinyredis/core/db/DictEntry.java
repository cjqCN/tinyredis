package com.github.cjqcn.tinyredis.core.db;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * 词典键值对
 */
@Setter
@Getter
@ToString
public class DictEntry {

    /**
     * 键
     */
    private Object key;

    /**
     * 值
     */
    private Object val;

    /**
     * 下一个节点
     */
    private DictEntry next;

}
