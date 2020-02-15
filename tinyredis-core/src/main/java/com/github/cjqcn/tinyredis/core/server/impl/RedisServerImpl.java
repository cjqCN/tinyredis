package com.github.cjqcn.tinyredis.core.server.impl;


import com.github.cjqcn.tinyredis.core.client.RedisClient;
import com.github.cjqcn.tinyredis.core.db.RedisDb;
import com.github.cjqcn.tinyredis.core.db.impl.RedisDbImpl;
import com.github.cjqcn.tinyredis.core.listen.ListenerManager;
import com.github.cjqcn.tinyredis.core.listen.impl.CommandListener;
import com.github.cjqcn.tinyredis.core.listen.impl.ListenerManagerImpl;
import com.github.cjqcn.tinyredis.core.server.Logo;
import com.github.cjqcn.tinyredis.core.server.RedisInfo;
import com.github.cjqcn.tinyredis.core.server.RedisServer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RedisServerImpl implements RedisServer {

    private Map<String, RedisClient> clients = new ConcurrentHashMap<>();
    private final RedisDb[] dbs = new RedisDb[16];
    private final ListenerManager listenerManager = new ListenerManagerImpl();

    public  RedisServerImpl() {
        listenerManager.addListener(new CommandListener());
    }
    @Override
    public void registerClient(RedisClient redisClient) {
        clients.put(redisClient.name(), redisClient);
        RedisClient.DataAccess dataAccess = redisClient.dataAccess();
        dataAccess.setServer(this);
        dataAccess.setCurDb(dbs[0]);
    }

    @Override
    public void removeClient(RedisClient redisClient) {
        clients.remove(redisClient.name());
        RedisClient.DataAccess dataAccess = redisClient.dataAccess();
        dataAccess.setServer(null);
        dataAccess.setCurDb(null);
    }

    @Override
    public RedisInfo info() {
        return null;
    }

    @Override
    public void init() {
        System.out.println(Logo.ascii_logo);
        for (int i = 0; i < dbs.length; i++) {
            dbs[i] = new RedisDbImpl(i);
        }
    }

    @Override
    public void destroy() {

    }

    @Override
    public RedisDb[] dbs() {
        return dbs;
    }

    @Override
    public boolean auth(String password) {
        return "password".equalsIgnoreCase(password);
    }

    @Override
    public ListenerManager listenerManager() {
        return listenerManager;
    }


}
