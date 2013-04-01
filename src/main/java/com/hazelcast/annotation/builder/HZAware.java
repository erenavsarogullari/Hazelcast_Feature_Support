package com.hazelcast.annotation.builder;

import com.hazelcast.annotation.HazelcastAware;
import com.hazelcast.common.HazelcastExtraException;
import com.hazelcast.common.Utilities;

/**
 * Date: 24/03/2013 13:22
 * Author Yusuf Soysal
 */
public class HZAware {

    public static <T> T initialize(Class<T> clz, Object... args) {
        HazelcastAware hzAwareAnnotation = clz.getAnnotation(HazelcastAware.class);

        if (hzAwareAnnotation == null) {
            throw new HazelcastExtraException("Class is not marked as @HazelcastAware");
        }

        T instance = Utilities.createAnnotatedInstance(clz, args);

        return instance;
    }
}
