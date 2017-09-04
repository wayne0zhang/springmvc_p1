package com.paxing.mq.memcacheq;

import com.google.code.yanf4j.core.impl.StandardSocketOption;
import com.paxing.mq.MQService;
import net.rubyeye.xmemcached.MemcachedClient;
import net.rubyeye.xmemcached.MemcachedClientBuilder;
import net.rubyeye.xmemcached.XMemcachedClientBuilder;
import net.rubyeye.xmemcached.command.BinaryCommandFactory;
import net.rubyeye.xmemcached.utils.AddrUtil;
import org.apache.log4j.Logger;

import java.io.IOException;

/**
 * @author wayne-zhang
 * @date 2017/9/4 19:52.
 */
public class MemcacheQService implements MQService {
    private static final Logger log = Logger.getLogger(MemcacheQService.class);
    private MemcachedClientBuilder builder;
    private MemcachedClient client;

    public MemcacheQService(String servers, int readThreadCount, long connectTimeout, long opTimeout) {
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
            client.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void append(String key, Object value) {
        try {
            client.set(key, 0, value);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public Object get(String key) {
        try {
            return client.get(key);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return null;
        }
    }
}
