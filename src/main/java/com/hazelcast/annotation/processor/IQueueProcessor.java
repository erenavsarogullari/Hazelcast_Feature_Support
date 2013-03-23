package com.hazelcast.annotation.processor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import com.hazelcast.annotation.IQueue;
import com.hazelcast.annotation.scanner.HazelcastAnnotationProcessor;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

public class IQueueProcessor implements HazelcastAnnotationProcessor {

	@Override
	public void process(Class<?> clazz, Annotation annotation) {
		for (Field field : clazz.getDeclaredFields()) {
			Annotation[] annotations = field.getAnnotations();
			for (Annotation tempAnnotation : annotations) {
				if (tempAnnotation.annotationType() == IQueue.class) {
					processDistributedQueue(clazz, (IQueue)tempAnnotation, field);
				} 
			}
		}
	}
	
	private void processDistributedQueue(Class<?> clazz, IQueue annotation, Field distributedQueueField) {
		try {
			HazelcastInstance instance = Hazelcast.getHazelcastInstanceByName("_hzInstance_1_dev");
			com.hazelcast.core.IQueue<Object> distributedQueue = instance.getQueue(annotation.name());
			distributedQueueField.setAccessible(true);
			distributedQueueField.set(clazz.newInstance(), distributedQueue);				
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
	}

}
