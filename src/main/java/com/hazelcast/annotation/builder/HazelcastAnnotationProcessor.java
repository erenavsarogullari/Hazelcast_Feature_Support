package com.hazelcast.annotation.builder;

import java.lang.annotation.Annotation;

import com.hazelcast.srv.IHazelcastService;

/**
 * Hazelcast Annotation Processor Interface
 *
 * @author Yusuf Soysal
 * @author Eren Avsarogullari
 * @since 17 March 2013
 * @version 1.0.0
 *
 */
public interface HazelcastAnnotationProcessor {
    
	
    void process(IHazelcastService hazelcastService, Class<?> clazz, Annotation annotation);
    
}
