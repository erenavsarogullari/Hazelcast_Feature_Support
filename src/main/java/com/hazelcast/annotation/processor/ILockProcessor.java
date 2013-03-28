package com.hazelcast.annotation.processor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import com.hazelcast.annotation.builder.HazelcastFieldAnnotationProcessor;
import com.hazelcast.annotation.data.ILock;
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
public class ILockProcessor implements HazelcastFieldAnnotationProcessor {

    @Override
    public void process(IHazelcastService hazelcastService, Object obj, Field field, Annotation annotation) {
        ILock lockAnnotation = (ILock) annotation;

        try {
        	HazelcastInstance hazelcastInstance = hazelcastService.getHazelcastInstanceByName(lockAnnotation.instanceName());
        	
        	if(hazelcastInstance == null) {
            	throw new HazelcastExtraException("HazelcastInstance " + lockAnnotation.instanceName() + "must not be null");
            }
        	
        	com.hazelcast.core.ILock distributedLock = hazelcastInstance.getLock(lockAnnotation.lockedObject());
            field.setAccessible(true);
            field.set(obj, distributedLock);
        	
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
    }

	@Override
	public void assignDistributedData(IHazelcastService hazelcastService,
			                                  Object obj, Field field, String instanceName, String typeName) {
		// TODO Auto-generated method stub
		
	}
}
