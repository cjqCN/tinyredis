package com.github.cjqcn.tinyredis.core.server.impl;

import com.github.cjqcn.tinyredis.core.client.RedisClient;
import com.github.cjqcn.tinyredis.core.listen.ListenerManager;
import com.github.cjqcn.tinyredis.core.listen.impl.CommandListener;
import com.github.cjqcn.tinyredis.core.listen.impl.ListenerManagerImpl;
import com.github.cjqcn.tinyredis.core.server.Logo;
import com.github.cjqcn.tinyredis.core.server.RedisConfig;
import com.github.cjqcn.tinyredis.core.server.RedisInfo;
import com.github.cjqcn.tinyredis.core.server.RedisServer;
import com.github.cjqcn.tinyredis.core.struct.RedisDb;
import com.github.cjqcn.tinyredis.core.struct.impl.RedisDbImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;

public class RedisServerImpl implements RedisServer {

    private static final Logger logger = LoggerFactory.getLogger(RedisServerImpl.class);
    private final Map<String, RedisClient> clients = new ConcurrentHashMap<>();
    private final ListenerManager listenerManager = new ListenerManagerImpl();
    private final RedisConfig redisConfig = new RedisConfig();
    private RedisDb[] dbs;
    private AtomicBoolean init = new AtomicBoolean(false);

    public RedisServerImpl() {
        listenerManager.addListener(new CommandListener());
    }

    @Override
    public void registerClient(RedisClient redisClient) {
        clients.put(redisClient.name(), redisClient);
        RedisClient.DataAccess dataAccess = redisClient.dataAccess();
        dataAccess.setServer(this);
        dataAccess.setCurDb(dbs[0]);
        dataAccess.setAuth(redisConfig.getPassword() == null);
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
        if (!init.compareAndSet(false, true)) {
            logger.info("Ignore init() call since it has already been init.");
        }
        System.out.println(Logo.ASCII_LOGO);
        dbs = new RedisDb[redisConfig.getDbNum()];
        for (int i = 0; i < dbs.length; i++) {
            dbs[i] = new RedisDbImpl(i);
        }
    }

    @Override
    public void destroy() {

    }

    @Override
    public RedisDb db(int index) {
        return dbs[index];
    }

    @Override
    public boolean auth(String password) {
        return redisConfig.getPassword().equals(password);
    }

    @Override
    public ListenerManager listenerManager() {
        return listenerManager;
    }

    @Override
    public RedisConfig redisConfig() {
        return redisConfig;
    }


}
