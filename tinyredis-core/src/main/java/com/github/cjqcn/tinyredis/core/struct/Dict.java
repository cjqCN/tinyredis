package com.github.cjqcn.tinyredis.core.struct;

public interface Dict<K, V> {

    V get(K key);

    void set(K key, V value);

    V setnx(K key, V value);

    boolean exist(K key);

    V remove(K key);


}
