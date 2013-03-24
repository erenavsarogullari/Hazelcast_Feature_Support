package com.hazelcast.annotation.processor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Set;

import com.hazelcast.annotation.builder.HazelcastFieldAnnotationProcessor;
import com.hazelcast.annotation.data.ISet;
import com.hazelcast.annotation.builder.HazelcastAnnotationProcessor;
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
			Set<HazelcastInstance> hazelcastInstances = hazelcastService.getAllHazelcastInstances();
			for(HazelcastInstance instance : hazelcastInstances) {
				com.hazelcast.core.ISet<Object> distributedSet = instance.getSet(setAnnotation.name());
				field.setAccessible(true);
				field.set(obj, distributedSet);
			}						
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}


}
