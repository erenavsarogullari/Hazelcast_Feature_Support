package com.hazelcast.annotation.processor;

import java.lang.annotation.Annotation;

import com.hazelcast.annotation.builder.HazelcastAnnotationProcessor;
import com.hazelcast.srv.IHazelcastService;

/**
 * Hazelcast MembershipListener Annotation Interface
 *
 * @author Yusuf Soysal
 * @author Eren Avsarogullari
 * @since 17 March 2013
 * @version 1.0.0
 *
 */
public class MembershipListenerProcessor implements HazelcastAnnotationProcessor {

	@Override
	public void process(IHazelcastService hazelcastService, Class<?> clazz, Annotation annotation) {
		
	}

}
