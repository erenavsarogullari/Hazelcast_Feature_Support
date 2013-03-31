package com.hazelcast.annotation.builder;

import com.hazelcast.common.ObjectCreator;

/**
 * Date: 31/03/2013 15:47
 * Author Yusuf Soysal
 */
public class DefaultObjectCreator implements ObjectCreator{
    @Override
    public <T> T createInstance(Class<T> clz) throws IllegalAccessException, InstantiationException {
        return clz.newInstance();
    }
}
