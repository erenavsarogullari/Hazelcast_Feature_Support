package com.hazelcast.annotation.processor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Set;

import com.hazelcast.annotation.IExecutorService;
import com.hazelcast.annotation.builder.HazelcastAnnotationProcessor;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.srv.IHazelcastService;

/**
 * Hazelcast EntryListener Annotation Processor
 *
 * @author Eren Avsarogullari
 * @author Yusuf Soysal
 * @since 17 March 2013
 * @version 1.0.0
 *
 */
public class ExecutorServiceProcessor implements HazelcastAnnotationProcessor {

	@Override
	public void process(IHazelcastService hazelcastService, Class<?> clazz, Annotation annotation) {
		for (Field field : clazz.getDeclaredFields()) {
			Annotation[] annotations = field.getAnnotations();
			for (Annotation tempAnnotation : annotations) {
				if (tempAnnotation.annotationType() == IExecutorService.class) {
					processExecutorService(hazelcastService, clazz, (IExecutorService)tempAnnotation, field);
				} 
			}
		}
	}
	
	private void processExecutorService(IHazelcastService hazelcastService, Class<?> clazz, IExecutorService annotation, Field field) {
		try {
			Set<HazelcastInstance> hazelcastInstances = hazelcastService.getAllHazelcastInstances();
			for(HazelcastInstance hazelcastInstance : hazelcastInstances) {
				java.util.concurrent.ExecutorService executorService = (annotation.name() != null)? hazelcastInstance.getExecutorService(annotation.name()) : hazelcastInstance.getExecutorService();
				field.setAccessible(true);
				field.set(clazz.newInstance(), executorService);
			}
							
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		}
	}
	
}
