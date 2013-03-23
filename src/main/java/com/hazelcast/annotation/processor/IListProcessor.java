package com.hazelcast.annotation.processor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Set;

import com.hazelcast.annotation.IList;
import com.hazelcast.annotation.ISet;
import com.hazelcast.annotation.builder.HazelcastAnnotationProcessor;
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
public class IListProcessor implements HazelcastAnnotationProcessor {

	@Override
	public void process(IHazelcastService hazelcastService, Class<?> clazz, Annotation annotation) {
		for (Field field : clazz.getDeclaredFields()) {
			Annotation[] annotations = field.getAnnotations();
			for (Annotation tempAnnotation : annotations) {
				if (tempAnnotation.annotationType() == IList.class) {
					processDistributedList(hazelcastService, clazz, (IList)tempAnnotation, field);
				} 
			}
		}
	}
	
	private void processDistributedList(IHazelcastService hazelcastService, Class<?> clazz, IList annotation, Field distributedListField) {
		try {
			Set<HazelcastInstance> hazelcastInstances = hazelcastService.getAllHazelcastInstances();
			for(HazelcastInstance instance : hazelcastInstances) {
				com.hazelcast.core.IList<Object> distributedList = instance.getList(annotation.name());
				distributedListField.setAccessible(true);
				distributedListField.set(clazz.newInstance(), distributedList);	
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
