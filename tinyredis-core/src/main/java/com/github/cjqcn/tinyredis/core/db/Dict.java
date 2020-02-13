package com.github.cjqcn.tinyredis.core.db;

public interface Dict<K, V> {

    V get(K key);

    void set(K key, V value);

    boolean exist(K key);

    void remove(K key);


}
