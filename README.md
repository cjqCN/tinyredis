# tinyredis

## 简介
用 java 实现的 redis, 继续更新。   
如果你感兴趣, 请点 [Star](https://github.com/cjqCN/tinyredis/stargazers)。  

----------------

## 已实现命令
- auth 
- monitor
- select 
- del
- get
- set
- setnx
- setex
- psetex
- mget

- expire
- ttl
- pttl
- type

- hset
- hget
- hdel

## 使用
### 启动
`com.github.cjqcn.tinyredis.remote.RemoteServer.main` 启动程序    
启动日志：
```log
[2020-02-15 21:01:49.584] - [INFO] - [com.github.cjqcn.tinyredis.remote.RemoteServer:74] - [Method = main] - [Started redis at address localhost/127.0.0.1:6379, cost 3112ms]```
```

### 访问

```cmd
127.0.0.1:6379> set key value
(error) NOAUTH Authentication required.
127.0.0.1:6379> auth password
OK
127.0.0.1:6379> set key value
OK
127.0.0.1:6379> get key
value
127.0.0.1:6379> select 2
OK
127.0.0.1:6379[2]> get key
NULL
127.0.0.1:6379[2]> select 0
OK
127.0.0.1:6379> get key
value
127.0.0.1:6379>
```

***tip:*** /doc/windows/redis-cli.exe 为 windows 下的 redis 客户端

