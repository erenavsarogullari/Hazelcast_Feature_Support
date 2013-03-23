package com.hazelcast.annotation.processor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import com.hazelcast.annotation.ExecutorService;
import com.hazelcast.annotation.scanner.HazelcastAnnotationProcessor;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

public class ExecutorServiceProcessor implements HazelcastAnnotationProcessor {

	@Override
	public void process(Class<?> clazz, Annotation annotation) {
		for (Field field : clazz.getDeclaredFields()) {
			Annotation[] annotations = field.getAnnotations();
			for (Annotation tempAnnotation : annotations) {
				if (tempAnnotation.annotationType() == ExecutorService.class) {
					processExecutorService(clazz, (ExecutorService)tempAnnotation, field);
				} 
			}
		}
	}
	
	private void processExecutorService(Class<?> clazz, ExecutorService annotation, Field field) {
		try {
			HazelcastInstance instance = Hazelcast.getHazelcastInstanceByName("_hzInstance_1_dev");			
			java.util.concurrent.ExecutorService executorService = (annotation.name() != null)? instance.getExecutorService(annotation.name()) : instance.getExecutorService();
			field.setAccessible(true);
			field.set(clazz.newInstance(), executorService);				
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
	}
	
}
