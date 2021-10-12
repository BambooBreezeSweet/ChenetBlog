package com.chen.website.utils;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 * @author ChenetChen
 * @since 2021/3/11 23:33
 */
@Component
public class RedisUtils {

    @Resource
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * 指定缓存失效的时间
     * @param key 键
     * @param time 时间
     * @return 状态
     */
    public boolean expire(String key, long time){
        return Boolean.TRUE.equals(redisTemplate.expire(key, time, TimeUnit.SECONDS));
    }

    /**
     * 根据key获取过期时间
     * @param key 键
     * @return 时间
     */
    public long getExpire(String key){
        if (hasKey(key)){
            return redisTemplate.getExpire(key, TimeUnit.SECONDS);
        }
        return 0;
    }

    /**
     * 判断key是否存在
     * @param key 键
     * @return 是否存在
     */
    public boolean hasKey(String key){
        return Boolean.TRUE.equals(redisTemplate.hasKey(key));
    }

    /**
     * 删除缓存
     * @param key 键
     */
    public void delete(String key){
        redisTemplate.delete(key);
    }

    //====================String====================

    /**
     * 缓存String类型
     * @param key 键
     * @param value 值
     */
    public void setString(String key, Object value){
        redisTemplate.opsForValue().set(key, value);
    }

    /**
     * 缓存String类型并设置过期时间
     * @param key 键
     * @param value 值
     * @param time 时间（秒）
     */
    public void setString(String key, Object value, long time){
        redisTemplate.opsForValue().set(key, value, time, TimeUnit.SECONDS);
    }

    /**
     * 读取String类型
     * @param key 键
     * @return 值
     */
    public Object getString(String key){
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 更新String类型
     * @param key 键
     * @param value 值
     */
    public void updateString(String key, String value){
        redisTemplate.opsForValue().getAndSet(key, value);
    }

    //====================List====================

    /**
     * 缓存List类型
     * @param key 键
     * @param list list值
     */
    public void setList(String key, List<Object> list){
        redisTemplate.opsForList().leftPush(key, list);
    }

    /**
     * 读取List类型缓存
     * @param key 键
     * @return list类型数据
     */
    public List<Object> getList(String key){
        return redisTemplate.opsForList().range(key,0,-1);
    }

    //====================Hash====================

    /**
     * 缓存Hash类型
     * @param key 键
     * @param map map类型值
     */
    public void setHash(String key, Map<Object, Object> map){
        redisTemplate.opsForHash().putAll(key, map);
    }

    /**
     * 读取Hash类型缓存
     * @param key 键
     * @return 获取所有Map
     */
    public Map<Object, Object> getHash(String key){
        return redisTemplate.opsForHash().entries(key);
    }
}