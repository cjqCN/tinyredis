package com.github.cjqcn.tinyredis.core.db;

import com.github.cjqcn.tinyredis.core.struct.Sds;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * key 驱逐池条目
 */
@Getter
@Setter
@ToString
public class EvictionPoolEntry {

    /**
     * key 空间时间
     */
    private long ide;

    /**
     * key 的值
     */
    private Sds name;
}
