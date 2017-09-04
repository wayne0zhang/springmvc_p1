package com.paxing.cache.memcached;

import com.google.code.yanf4j.core.impl.StandardSocketOption;
import com.paxing.cache.CacheService;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.command.BinaryCommandFactory;
import net.rubyeye.xmemcached.utils.AddrUtil;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.List;
import java.util.Map;

/**
 * @author wayne-zhang
 * @date 2017/9/4 12:09.
 */
public class MemcachedService implements CacheService {
    private static final Logger logger = Logger.getLogger(MemcachedService.class);
    private MemcachedClient client;
    private MemcachedClientBuilder builder;

    public MemcachedService(String servers, int readThreadCount, long connectTimeout, long opTimeout) {
        this.builder = new XMemcachedClientBuilder(AddrUtil.getAddresses(servers));
        this.builder.setConnectionPoolSize(1);
        this.builder.setCommandFactory(new BinaryCommandFactory());
        this.builder.getConfiguration().setReadThreadCount(readThreadCount);
        this.builder.getConfiguration().setHandleReadWriteConcurrently(true);
        this.builder.setSocketOption(StandardSocketOption.SO_RCVBUF, Integer.valueOf(32768));
        this.builder.setSocketOption(StandardSocketOption.SO_SNDBUF, Integer.valueOf(16384));
        this.builder.setSocketOption(StandardSocketOption.TCP_NODELAY, false);

        try {
            this.client = builder.build();
            client.setOpTimeout(opTimeout);
            client.setConnectTimeout(connectTimeout);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void destroy() {
        try {
            this.client.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void add(String key, Object value) {
        add(key, value, 0);
    }

    public void add(String key, Object value, int expiry) {
        try {
            client.addWithNoReply(key, expiry, value);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public boolean addWithReply(String key, Object value) {
        return addWithReply(key, value, 0);
    }

    public boolean addWithReply(String key, Object value, int expiry) {
        try {
            return client.add(key, expiry, value);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    public void replace(String key, Object value) {
        replace(key, value, 0);
    }

    public void replace(String key, Object value, int expiry) {
        try {
            client.replaceWithNoReply(key, expiry, value);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public boolean replaceWithReply(String key, Object value) {
        return replaceWithReply(key, value, 0);
    }

    public boolean replaceWithReply(String key, Object value, int expiry) {
        try {
            return client.replace(key, expiry, value);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    public void set(String key, Object value) {
        set(key, value, 0);
    }

    public void set(String key, Object value, int expiry) {
        try {
            client.setWithNoReply(key, expiry, value);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public boolean setWithReply(String key, Object value) {
        return setWithReply(key, value, 0);
    }

    public boolean setWithReply(String key, Object value, int expiry) {
        try {
            return client.set(key, expiry, value);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    public void delete(String key) {
        try {
            client.deleteWithNoReply(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public boolean deleteWithReply(String key) {
        try {
            return client.delete(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    public Object get(String key) {
        Object value = null;
        try {
            value = client.get(key);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return value;
    }

    public Map<String, Object> gets(List<String> keys) {
        Map<String, Object> values = null;
        try {
            values = client.get(keys);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return values;
    }

    public Object getAndTouch(String key, int expiry) {
        Object value = null;
        try {
            value = client.getAndTouch(key, expiry);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return value;
    }

    public boolean touch(String key, int expiry) {
        try {
            return this.client.touch(key, expiry);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return false;
        }
    }

    public void flushAll() {
        try {
            client.flushAll();
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    public void flushAll(List<String> servers) {
        if (servers != null && servers.size() > 0) {
            try {
                for (String server : servers) {
                    String[] servTemp = server.split(":");
                    client.flushAll(new InetSocketAddress(servTemp[0], Integer.parseInt(servTemp[1])));
                }
            } catch (Exception e) {
                logger.error(e.getMessage(), e);
            }
        }
    }
}
