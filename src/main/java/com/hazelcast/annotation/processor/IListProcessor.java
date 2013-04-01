package com.hazelcast.annotation.processor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import com.hazelcast.annotation.builder.HazelcastFieldAnnotationProcessor;
import com.hazelcast.annotation.data.IList;
import com.hazelcast.common.HazelcastExtraException;
import com.hazelcast.core.Hazelcast;
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
        	
        	HazelcastInstance hazelcastInstance = hazelcastService.getCurrentHazelcastInstance(listAnnotation.instanceName());
        	
        	if(hazelcastInstance == null) {
            	throw new HazelcastExtraException("HazelcastInstance " + listAnnotation.instanceName() + "must not be null");
            }
        	com.hazelcast.core.IList<Object> distributedList = hazelcastInstance.getList(listAnnotation.name());
            field.setAccessible(true);
            field.set(obj, distributedList);
            
        } catch (IllegalArgumentException e) {
            throw new HazelcastExtraException("Cannot set value to  " + obj.getClass().getName() + "'s " + field.getName() + " field", e);
        } catch (IllegalAccessException e) {
            throw new HazelcastExtraException("Cannot access " + obj.getClass().getName() + "'s " + field.getName() + " field", e);
        }
    }

    @Override
    public void assignDistributedData(IHazelcastService hazelcastService, Object obj, Field field, String instanceName, String typeName){
        HazelcastInstance hazelcastInstance = hazelcastService.getCurrentHazelcastInstance(instanceName);

        com.hazelcast.core.IList<Object> distributedList = hazelcastInstance.getList(typeName);
        if(distributedList != null) {
            field.setAccessible(true);
            try {
                field.set(obj, distributedList);
            } catch (IllegalAccessException e) {
                throw new HazelcastExtraException("Cannot access " + obj.getClass().getName() + "'s " + field.getName() + " field", e);
            }
        }
    }
}
