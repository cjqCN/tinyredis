package com.github.cjqcn.tinyredis.core.listen.impl;

import com.github.cjqcn.tinyredis.core.listen.Listener;
import com.github.cjqcn.tinyredis.core.util.TypeParameterMatcher;

public abstract class AbstractListener<T> implements Listener {

    private final TypeParameterMatcher matcher;

    public AbstractListener() {
        matcher = TypeParameterMatcher.find(this, AbstractListener.class, "T");
    }


    @Override
    public void accept(Object event) {
        if (matcher.match(event)) {
            accept0((T) event);
        }
    }

    public abstract void accept0(T event);
}
