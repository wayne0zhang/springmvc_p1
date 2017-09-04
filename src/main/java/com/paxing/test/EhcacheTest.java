package com.paxing.test;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

/**
 * @author wayne-zhang
 * @date 2017/8/9 17:46.
 */
public class EhcacheTest {
    public static void main(String... args) {
        // 根据xml文件创建cache管理器
        CacheManager manager = CacheManager.create("./src/main/resources/ehcache.xml");
        Cache cache = manager.getCache("a");
        Element element = new Element("e1", "test");
        cache.put(element);//添加元素

        Element element1 = cache.get("e1");
        System.out.println(element1);
        System.out.println(element1.getObjectValue());

        cache.flush();
        manager.shutdown();// 关闭缓存管理器
    }
}
