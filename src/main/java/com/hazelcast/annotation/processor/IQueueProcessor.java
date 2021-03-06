package com.hazelcast.annotation.processor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import com.hazelcast.annotation.builder.HazelcastFieldAnnotationProcessor;
import com.hazelcast.annotation.data.IQueue;
import com.hazelcast.common.HazelcastExtraException;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.srv.IHazelcastService;

/**
 * Hazelcast IQueue Annotation Processor
 *
 * @author Eren Avsarogullari
 * @author Yusuf Soysal
 * @since 17 March 2013
 * @version 1.0.0
 *
 */
public class IQueueProcessor implements HazelcastFieldAnnotationProcessor {

    @Override
    public void process(IHazelcastService hazelcastService, Object obj, Field field, Annotation annotation) {
        IQueue queueAnnotation = (IQueue) annotation;

        try {
        	HazelcastInstance hazelcastInstance = hazelcastService.getCurrentHazelcastInstance(queueAnnotation.instanceName());
        	
        	if(hazelcastInstance == null) {
            	throw new HazelcastExtraException("HazelcastInstance " + queueAnnotation.instanceName() + "must not be null");
            }
        	
            com.hazelcast.core.IQueue<Object> distributedQueue = hazelcastInstance.getQueue(queueAnnotation.name());
            if(distributedQueue != null) {
                field.setAccessible(true);
                field.set(obj, distributedQueue);
            }
            
        } catch (IllegalArgumentException e) {
            throw new HazelcastExtraException("Cannot set value to  " + obj.getClass().getName() + "'s " + field.getName() + " field", e);
        } catch (IllegalAccessException e) {
            throw new HazelcastExtraException("Cannot access " + obj.getClass().getName() + "'s " + field.getName() + " field", e);
        }
    }

    @Override
    public void assignDistributedData(IHazelcastService hazelcastService, Object obj, Field field, String instanceName, String typeName){
        HazelcastInstance hazelcastInstance = hazelcastService.getCurrentHazelcastInstance(instanceName);

        com.hazelcast.core.IQueue<Object> distributedQueue = hazelcastInstance.getQueue(typeName);
        if(distributedQueue != null) {
            field.setAccessible(true);
            try {
                field.set(obj, distributedQueue);
            } catch (IllegalAccessException e) {
                throw new HazelcastExtraException("Cannot access " + obj.getClass().getName() + "'s " + field.getName() + " field", e);
            }
        }
    }
}
