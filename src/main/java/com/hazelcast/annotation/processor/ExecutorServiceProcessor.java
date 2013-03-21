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
		Field executorServiceField = null;
		
		for (Field field : clazz.getDeclaredFields()) {
			Annotation[] annotations = field.getAnnotations();
			for (Annotation tempAnnotation : annotations) {
				if (tempAnnotation.annotationType() == ExecutorService.class) {
					executorServiceField = field;
				} 
			}
		}

		if (executorServiceField != null) {
			try {
				HazelcastInstance instance = Hazelcast.getHazelcastInstanceByName("_hzInstance_1_dev");
				java.util.concurrent.ExecutorService executorService = instance.getExecutorService();
				executorServiceField.setAccessible(true);
				executorServiceField.set(clazz.newInstance(), executorService);				
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			} catch (InstantiationException e) {
				e.printStackTrace();
			}
		}
	}


}