package com.github.cjqcn.tinyredis.core.struct.impl;

import com.github.cjqcn.tinyredis.core.struct.Dict;
import com.github.cjqcn.tinyredis.core.struct.Type;
import com.github.cjqcn.tinyredis.core.struct.RedisObject;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class BaseDict<K, V> implements Dict<K, V>, RedisObject<Dict<K, V>> {

    private Map<K, V> store = new ConcurrentHashMap<>();

    @Override
    public V get(K key) {
        return store.get(key);
    }

    @Override
    public void set(K key, V value) {
        store.put(key, value);
    }

    @Override
    public V setnx(K key, V value) {
        return store.putIfAbsent(key, value);
    }

    @Override
    public boolean exist(K key) {
        return store.containsKey(key);
    }

    @Override
    public V remove(K key) {
        return store.remove(key);
    }

    @Override
    public long size() {
        return store.size();
    }

    @Override
    public Type type() {
        return Type.map;
    }

    @Override
    public Dict<K, V> get() {
        return this;
    }
}
