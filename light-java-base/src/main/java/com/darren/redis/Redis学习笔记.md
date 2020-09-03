解压命令:tar -zxvf redis-3.0.4.tar.gz
Redis关闭与启动
redis-cli -h 127.0.0.1 -p 6801 shutdown

cd /data/redis/instance/6801/
./../../bin/redis-server redis.conf

select 0 切换数据库
dbsize
flushdb
flushall

Redis的key相关操作
keys *	列出所有key
exists k1	是否存在key
set k3 v100
type k3
get k3
ttl k3
expire k3 10 

Redis的list相关操作
LPUSH list1 1 2 3 4 5
RPUSH list2 1 2 3 4 5
LRANGE list2 0 -1
lpop list1
rpop list1
lindex list1 0
list长度:llen list1	
lrem list3 2 3	删除N个value
ltrim list4 0 4 截取指定范围的值再赋值给key
rpoplpush list1 list2 从list1右出栈并左入栈list2
lset list1 1 ss 给指定索引位重新赋值
linsert list1 before ss 2	在指定值前添加值

Redis的set相关操作
向集合添加元素：sadd set1 1 1 2 3
集合所有元素：smembers set1
是否存在集合中：sismember 4
集合长度：scard set1	
移除指定元素：srem set1 2
随机数：srandmember set 3
出栈：spop set1
移动set2的元素到set1：smove set2 set1 x
集合的数学操作
差集：sdiff set1 set2
交集：sinter set1 set2
并集：sunion set1 set2

Redis的hash相关操作
逐次设置值：hset user1 name Darren
逐次获取值：hget user1 name
批量设置值：hmset user2 name caroline age 22 tall 160
批量获取值：hmget user2 name age tall
获取全部键值：hgetall user1
删除键值：hdel user1 name
长度：hlen user1
是否存在：hexists user name
键集合：hkeys user1
值集合：hvals user2
整数自增：hincrby user2 age 1
浮点自增：hincrbyfloat user3 score 0.5
如果不存在则设置：hsetnx user3 age 25

Redis的Zset相关操作
zadd zset1 40 v4 45 v5 49 v6 55 v7
zrange zset1 0 -1
zrange zset1 0 -1 withscores
zrangebyscore zset1 20 50
zrangebyscore zset1 20 50 limit 2 3
zrem zset1 v7
zcard zset1
zcount zset1 30 50
zrank zset1 v4
zscore zset1 v4
zrevrank zset1 v4
zrevrange zset1 0 -1
zrevrangebyscore zset1 50 30

主从配置（配从不配主）
Master以写为主，Slave以读为主-读写分离，容灾备份
slaveof 127.0.0.1 6801
info replication
slaveof no one 反客为主

哨兵模式（主机挂掉后自动投票选举新主机，原主机恢复后作为从机继续工作）
sentinel monitor 被监控数据库名字(自己起名字) 127.0.0.1 6379 1
redis-sentinel /myredis/sentinel.conf 

##配置文件解析

是否后台启动
daemonize yes

空闲多少秒后关闭连接
timeout 0

探活间隔，0不检测，单位秒
tcp-keepalive 0
最大连接数默认10000
maxclients 10000
最大内存
maxmemory <bytes>
最大连接数默认10000
maxclients 10000

超出最大内存移除策略
maxmemory-policy noeviction
 volatile-lru -> remove the key with an expire set using an LRU algorithm
 allkeys-lru -> remove any key according to the LRU algorithm
 volatile-random -> remove a random key with an expire set
 allkeys-random -> remove a random key, any key
 volatile-ttl -> remove the key with the nearest expire time (minor TTL)
 noeviction -> don't expire at all, just return an error on write operations
（1）volatile-lru：使用LRU(Least Recently Used)算法移除key，只对设置了过期时间的键
（2）allkeys-lru：使用LRU算法移除key
（3）volatile-random：在过期集合中移除随机的key，只对设置了过期时间的键
（4）allkeys-random：移除随机的key
（5）volatile-ttl：移除那些TTL值最小的key，即那些最近要过期的key
（6）noeviction：不进行移除。针对写操作，只是返回错误信息


设置样本数量，LRU算法和最小TTL算法都并非是精确的算法，而是估算值，所以你可以设置样本的大小，redis默认会检查这么多个key并选择其中LRU的那个
maxmemory-samples 5

文件追加(AOF)策略
appendfsync always
appendfsync everysec
appendfsync no

AOF重写机制













 