package com.paxing.mq;

/**
 * @author wayne-zhang
 * @date 2017/9/4 19:48.
 */
public interface MQService {
    void append(String key, Object value);

    Object get(String key);
}
