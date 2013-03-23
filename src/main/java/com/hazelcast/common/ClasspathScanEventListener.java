package com.hazelcast.common;

/**
 * Date: 23/03/2013 16:20
 * Author Yusuf Soysal
 */
public interface ClasspathScanEventListener {
    void classFound(Class<?> clazz);
}
