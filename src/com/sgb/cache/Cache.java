package com.sgb.cache;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Logger;

/**
 * 通用缓存类(只要有修改就清空缓存)
 * @author YXK
 * @param <T>
 *
 */
public class Cache<T> {
	private final static Logger logger = Logger.getLogger(Cache.class.getName());
    /**
     * 缓存
     */
    private Map<String, T> cache;
    /**
     * 访问计数器
     */
    private long visitedCount;
    /**
     * 命中计数器
     */
    private long hitCount;
 
    /**
     * 构造函数
     * @param maxSize 缓存最大容量
     */
    public Cache(final int maxSize) {
        cache = new LinkedHashMap<String, T>(1, 0.75F, true) {
 
            @Override
            protected boolean removeEldestEntry(Entry<String, T> eldest) {
                return size() > maxSize;
            }
        };
        clear();
    }
 
    /**
     * 取出一个值
     * @param key 键
     * @return 值/空
     */
    public T get(String key) {
        if (key == null) {
            return null;
        }
 
        visitedCount++;
        if (cache.containsKey(key)) {
            hitCount++;
            return cache.get(key);
        } else {
            return null;
        }
    }
 
    /**
     * 存入一个值
     * @param key 键
     * @param value 值
     */
    public synchronized void put(String key, T value) {
        if ((key == null) || (value == null)) {
            return;
        }
 
        cache.put(key, value);
    }
 
    /**
     * 清除缓存，复位计数器
     */
    public void clear() {
        cache.clear();
        visitedCount = 0;
        hitCount = 0;
    }
 
    /**
     * 获取命中计数
     * @return 命中计数
     */
    public long getHitCount() {
        return hitCount;
    }
 
    /**
     * 获取访问计数
     * @return 访问计数
     */
    public long getVisitedCount() {
        return visitedCount;
    }
 
    /**
     * 执行一个任务，如果在缓存中有对应的值，那么直接返回，否则执行任务并把输出保存入缓存
     * @param task 任务
     * @return 任务返回值
     */
    public T doCachedTask(CachedTask<T> task) {
        String key = task.getKey();
        T value = get(key);
        if (value == null) {
            value = task.run();
            put(key, value);
        }
        return value;
    }
 
    /**
     * 返回缓存内容
     * @return 缓存内容
     */
    @Override
    public String toString() {
        StringBuilder str = new StringBuilder("\n");
        for (Map.Entry<String, T> entry : cache.entrySet()) {
            str.append("Key: " + entry.getKey() + "\n");
            str.append("Value: " + entry.getValue() + "\n");
        }
        return str.toString();
    }
}
