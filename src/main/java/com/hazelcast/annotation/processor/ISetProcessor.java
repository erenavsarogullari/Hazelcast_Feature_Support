package com.hazelcast.annotation.processor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import com.hazelcast.annotation.builder.HazelcastFieldAnnotationProcessor;
import com.hazelcast.annotation.data.ISet;
import com.hazelcast.common.HazelcastExtraException;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.srv.IHazelcastService;

/**
 * Hazelcast ISet Annotation Processor
 *
 * @author Eren Avsarogullari
 * @author Yusuf Soysal
 * @since 17 March 2013
 * @version 1.0.0
 *
 */
public class ISetProcessor implements HazelcastFieldAnnotationProcessor {

    @Override
    public void process(IHazelcastService hazelcastService, Object obj, Field field, Annotation annotation) {
        ISet setAnnotation = (ISet) annotation;

		try {
			HazelcastInstance hazelcastInstance = hazelcastService.getCurrentHazelcastInstance(setAnnotation.instanceName());
        	
			if(hazelcastInstance == null) {
            	throw new HazelcastExtraException("HazelcastInstance " + setAnnotation.instanceName() + "must not be null");
            }
        	
    		com.hazelcast.core.ISet<Object> distributedSet = hazelcastInstance.getSet(setAnnotation.name());
			field.setAccessible(true);
			field.set(obj, distributedSet);
			
        } catch (IllegalArgumentException e) {
            throw new HazelcastExtraException("Cannot set value to  " + obj.getClass().getName() + "'s " + field.getName() + " field", e);
        } catch (IllegalAccessException e) {
            throw new HazelcastExtraException("Cannot access " + obj.getClass().getName() + "'s " + field.getName() + " field", e);
        }
	}

    @Override
    public void assignDistributedData(IHazelcastService hazelcastService, Object obj, Field field, String instanceName, String typeName){
        HazelcastInstance hazelcastInstance = hazelcastService.getCurrentHazelcastInstance(instanceName);

        com.hazelcast.core.ISet<Object> distributedSet = hazelcastInstance.getSet(typeName);
        if(distributedSet != null) {
            field.setAccessible(true);
            try {
                field.set(obj, distributedSet);
            } catch (IllegalAccessException e) {
                throw new HazelcastExtraException("Cannot access " + obj.getClass().getName() + "'s " + field.getName() + " field", e);
            }
        }
    }

}
