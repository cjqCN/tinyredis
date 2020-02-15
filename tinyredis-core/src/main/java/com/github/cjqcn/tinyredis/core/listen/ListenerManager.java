package com.github.cjqcn.tinyredis.core.listen;

public interface ListenerManager extends Listener {

    void addListener(Listener listener);

    void remove(Listener listener);

}
