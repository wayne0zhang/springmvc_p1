package com.paxing.cache;

import java.util.List;
import java.util.Map;

/**
 * @author wayne-zhang
 * @date 2017/9/4 11:50.
 */
public interface CacheService {
    void add(String key, Object value);

    void add(String key, Object value, int expiry);

    boolean addWithReply(String key, Object value);

    boolean addWithReply(String key, Object value, int expiry);

    void replace(String key, Object value);

    void replace(String key, Object value, int expiry);

    boolean replaceWithReply(String key, Object value);

    boolean replaceWithReply(String key, Object value, int expiry);

    void set(String key, Object value);

    void set(String key, Object value, int expiry);

    boolean setWithReply(String key, Object value);

    boolean setWithReply(String key, Object value, int expiry);

    void delete(String key);

    boolean deleteWithReply(String key);

    Object get(String key);

    Map<String, Object> gets(List<String> keys);

    Object getAndTouch(String key, int expiry);

    /**
     * 更新有效期
     *
     * @param key
     * @param expiry
     * @return
     */
    boolean touch(String key, int expiry);

    /**
     * 使失效
     */
    void flushAll();

    void flushAll(List<String> servers);
}
