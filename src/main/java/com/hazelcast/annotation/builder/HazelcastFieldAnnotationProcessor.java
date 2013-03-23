package com.hazelcast.annotation.builder;

import com.hazelcast.srv.IHazelcastService;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Date: 23/03/2013 17:02
 * Author Yusuf Soysal
 */
public interface HazelcastFieldAnnotationProcessor {

    void process(IHazelcastService hazelcastService, Class<?> clazz, Field field, Annotation annotation);

}
