package com.hazelcast.common;

/**
 * Date: 31/03/2013 15:46
 * Author Yusuf Soysal
 */
public interface ObjectCreator {
    <T> T createInstance(Class<T> clz) throws IllegalAccessException, InstantiationException;
}
