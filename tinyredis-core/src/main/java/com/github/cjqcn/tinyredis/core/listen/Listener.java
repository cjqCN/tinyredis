package com.github.cjqcn.tinyredis.core.listen;

public interface Listener {
    void accept(Object event);
}
