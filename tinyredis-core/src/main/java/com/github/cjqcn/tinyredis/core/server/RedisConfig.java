package com.github.cjqcn.tinyredis.core.server;

public final class RedisConfig {

    private static final String DEFAULT_HOST = "localhost";
    private static final int DEFAULT_PORT = 6379;
    private static final int DEFAULT_DB_NUM = 16;
    private static final String DEFAULT_PASSWORD = "root";

    private String host = DEFAULT_HOST;
    private int port = DEFAULT_PORT;
    private int dbNum = DEFAULT_DB_NUM;
    private String password = DEFAULT_PASSWORD;

    public String getHost() {
        return host;
    }

    public RedisConfig setHost(String host) {
        this.host = host;
        return this;
    }

    public int getPort() {
        return port;
    }

    public RedisConfig setPort(int port) {
        this.port = port;
        return this;
    }

    public int getDbNum() {
        return dbNum;
    }

    public RedisConfig setDbNum(int dbNum) {
        this.dbNum = dbNum;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public RedisConfig setPassword(String password) {
        this.password = password;
        return this;
    }

    @Override
    public String toString() {
        return "RedisConfig{" +
                "host='" + host + '\'' +
                ", port=" + port +
                ", dbNum=" + dbNum +
                ", password='" + password + '\'' +
                '}';
    }
}
