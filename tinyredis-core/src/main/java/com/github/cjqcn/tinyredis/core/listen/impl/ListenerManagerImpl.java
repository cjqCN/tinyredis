package com.github.cjqcn.tinyredis.core.listen.impl;

import com.github.cjqcn.tinyredis.core.listen.Listener;
import com.github.cjqcn.tinyredis.core.listen.ListenerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public final class ListenerManagerImpl implements ListenerManager {

    private final Logger logger = LoggerFactory.getLogger(ListenerManagerImpl.class);
    private final Executor executor = Executors.newSingleThreadExecutor();
    private Set<Listener> listeners = new HashSet<>();


    private void accept0(final Object event) {
        if (!listeners.isEmpty()) {
            for (Listener listener : listeners) {
                listener.accept(event);
            }
        }
    }

    @Override
    public void addListener(Listener listener) {
        listeners.add(listener);
    }

    @Override
    public void remove(Listener listener) {
        listeners.remove(listener);
    }

    @Override
    public void removeAll() {
        listeners.clear();
    }

    @Override
    public void accept(Object event) {
        executor.execute(() -> {
            accept0(event);
        });
    }
}
