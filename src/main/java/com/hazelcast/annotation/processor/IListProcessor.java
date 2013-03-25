package com.hazelcast.annotation.processor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Set;

import com.hazelcast.annotation.builder.HazelcastFieldAnnotationProcessor;
import com.hazelcast.annotation.data.IList;
import com.hazelcast.annotation.builder.HazelcastAnnotationProcessor;
import com.hazelcast.common.HazelcastExtraException;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.srv.IHazelcastService;

/**
 * Hazelcast IList Annotation Processor
 *
 * @author Eren Avsarogullari
 * @author Yusuf Soysal
 * @since 17 March 2013
 * @version 1.0.0
 *
 */
public class IListProcessor implements HazelcastFieldAnnotationProcessor {

    @Override
    public void process(IHazelcastService hazelcastService, Object obj, Field field, Annotation annotation) {
        IList listAnnotation = (IList) annotation;

        try {
            Set<HazelcastInstance> hazelcastInstances = hazelcastService.getAllHazelcastInstances();
            for(HazelcastInstance instance : hazelcastInstances) {
                com.hazelcast.core.IList<Object> distributedList = instance.getList(listAnnotation.name());
                field.setAccessible(true);
                field.set(obj, distributedList);
            }
        } catch (IllegalArgumentException e) {
            throw new HazelcastExtraException("Cannot set value to  " + obj.getClass().getName() + "'s " + field.getName() + " field", e);
        } catch (IllegalAccessException e) {
            throw new HazelcastExtraException("Cannot access " + obj.getClass().getName() + "'s " + field.getName() + " field", e);
        }
    }
}
