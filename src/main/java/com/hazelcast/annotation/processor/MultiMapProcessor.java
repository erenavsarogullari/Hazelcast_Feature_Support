package com.hazelcast.annotation.processor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import com.hazelcast.annotation.builder.HazelcastFieldAnnotationProcessor;
import com.hazelcast.annotation.data.MultiMap;
import com.hazelcast.common.HazelcastExtraException;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.srv.IHazelcastService;

/**
 * Hazelcast MultiMap Annotation Processor
 *
 * @author Eren Avsarogullari
 * @author Yusuf Soysal
 * @since 17 March 2013
 * @version 1.0.0
 *
 */
public class MultiMapProcessor implements HazelcastFieldAnnotationProcessor {

    @Override
    public void process(IHazelcastService hazelcastService, Object obj, Field field, Annotation annotation) {
    	MultiMap multiMapAnnotation = (MultiMap) annotation;

		try {
			HazelcastInstance hazelcastInstance = hazelcastService.getCurrentHazelcastInstance(multiMapAnnotation.instanceName());
        	
        	if(hazelcastInstance == null) {
            	throw new HazelcastExtraException("HazelcastInstance " + multiMapAnnotation.instanceName() + "must not be null");
            }
			
        	com.hazelcast.core.MultiMap multiMap = hazelcastInstance.getMultiMap(multiMapAnnotation.name());
			field.setAccessible(true);
			field.set(obj, multiMap);
			
        } catch (IllegalArgumentException e) {
            throw new HazelcastExtraException("Cannot set value to  " + obj.getClass().getName() + "'s " + field.getName() + " field", e);
        } catch (IllegalAccessException e) {
            throw new HazelcastExtraException("Cannot access " + obj.getClass().getName() + "'s " + field.getName() + " field", e);
        }
	}

    @Override
    public void assignDistributedData(IHazelcastService hazelcastService, Object obj, Field field, String instanceName, String typeName){
        // nothing to do here
    }
}
