package com.example.redis.dao;

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
     * 设置超期时间
     */
    boolean setExpire(String key, long expire);

    /**
     * 获取过期时间
     */
    Long getExpire(String key);

    /**
     * 删除数据
     */
    void remove(String key);

    /**
     * 自增操作
     * @param key
     * @param delta 自增步长
     * @return
     */
    Long increment(String key, long delta);
}
