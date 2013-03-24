package com.hazelcast.annotation.processor;

import com.hazelcast.annotation.builder.HazelcastFieldAnnotationProcessor;
import com.hazelcast.annotation.data.HZInstance;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.srv.IHazelcastService;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Date: 23/03/2013 17:39
 * Author Yusuf Soysal
 */
public class HZInstanceProcessor implements HazelcastFieldAnnotationProcessor {
    @Override
    public void process(IHazelcastService hazelcastService, Object obj, Field field, Annotation annotation) {
        HZInstance hzInstanceAnnotation = (HZInstance) annotation;

        HazelcastInstance instance = Hazelcast.getHazelcastInstanceByName(hzInstanceAnnotation.value());
        try {
            field.setAccessible(true);
            field.set(obj, instance);

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }
}
