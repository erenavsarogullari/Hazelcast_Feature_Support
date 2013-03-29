package com.hazelcast.annotation.processor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import com.hazelcast.annotation.builder.HazelcastFieldAnnotationProcessor;
import com.hazelcast.annotation.data.ITopic;
import com.hazelcast.common.HazelcastExtraException;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.srv.IHazelcastService;

/**
 * Hazelcast Topic Annotation Processor
 *
 * @author Eren Avsarogullari
 * @author Yusuf Soysal
 * @since 25 March 2013
 * @version 1.0.0
 *
 */
public class ITopicProcessor implements HazelcastFieldAnnotationProcessor {

    @Override
    public void process(IHazelcastService hazelcastService, Object obj, Field field, Annotation annotation) {
    	
    	ITopic topicAnnotation = (ITopic) annotation;

        try {
        	HazelcastInstance hazelcastInstance = hazelcastService.getHazelcastInstanceByName(topicAnnotation.instanceName());
        	
        	if(hazelcastInstance == null) {
            	throw new HazelcastExtraException("HazelcastInstance " + topicAnnotation.instanceName() + "must not be null");
            }
        	
        	com.hazelcast.core.ITopic distributedTopic = hazelcastInstance.getTopic(topicAnnotation.name());
            if(distributedTopic != null) {
               field.setAccessible(true);
               field.set(obj, distributedTopic);
            }
        	
        } catch (IllegalArgumentException e) {
        	throw new HazelcastExtraException("Cannot set value to  " + obj.getClass().getName() + "'s " + field.getName() + " field", e);
        } catch (IllegalAccessException e) {
        	throw new HazelcastExtraException("Cannot access " + obj.getClass().getName() + "'s " + field.getName() + " field", e);
        }
    }

	@Override
	public void assignDistributedData(IHazelcastService hazelcastService,
			Object obj, Field field, String instanceName, String typeName) {
		// TODO Auto-generated method stub
		
	}
}
