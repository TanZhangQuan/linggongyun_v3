package com.example.redis.dao.impl;

import com.example.redis.dao.RedisDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.concurrent.TimeUnit;

@Repository
public class RedisDaoImpl implements RedisDao {
    @Autowired
    private StringRedisTemplate redisTemplate;
    /**
     * 存储数据
     */
    @Override
    public void set(String key, String value) {
        redisTemplate.opsForValue().set(key,value);
    }
    /**
     * 获取数据
     */
    @Override
    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }
    /**
     * 设置超期时间
     */
    @Override
    public boolean setExpire(String key, long expire) {
        return redisTemplate.expire(key,expire, TimeUnit.SECONDS);
    }

    /**
     * 根据key获取过期时间
     * @param key
     * @return
     */
    @Override
    public Long getExpire(String key) {
        return redisTemplate.getExpire(key);
    }

    /**
     * 删除数据
     */
    @Override
    public void remove(String key) {
        redisTemplate.delete(key);
    }
    /**
     * 自增操作
     * @param key
     * @param delta 自增步长
     * @return
     */
    @Override
    public Long increment(String key, long delta) {
        return null;
    }
}
