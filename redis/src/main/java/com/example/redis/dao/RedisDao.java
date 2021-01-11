package com.example.redis.dao;

import java.util.concurrent.TimeUnit;

public interface RedisDao {

    /**
     * 存储数据
     */
    void set(String key, String value);

    /**
     * 获取数据
     */
    String get(String key);

    /**
     * 设置过期时间
     */
    boolean setExpire(String key, long expire);

    /**
     * 获取过期时间
     */
    Long getExpire(String key);

    /**
     * 设置过期时间
     */
    public boolean setExpire(String key, long expire, TimeUnit timeUnit);

    /**
     * 删除数据
     */
    void remove(String key);

    /**
     * 自增操作
     *
     * @param key
     * @param delta 自增步长
     * @return
     */
    Long increment(String key, long delta);

    /**
     * 加锁
     *
     * @param lockKey   加锁的Key
     * @param timeStamp 时间戳：当前时间+超时时间
     * @return
     */
    boolean lock(String lockKey, long timeStamp);

    /**
     * 释放锁
     *
     * @param lockKey
     * @param timeStamp
     */
    void release(String lockKey, long timeStamp);
}
