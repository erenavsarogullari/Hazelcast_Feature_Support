package com.hazelcast.annotation.processor;

import java.lang.annotation.Annotation;

import com.hazelcast.annotation.builder.HazelcastAnnotationProcessor;
import com.hazelcast.srv.IHazelcastService;

/**
 * Hazelcast InstanceListener Annotation Processor
 *
 * @author Eren Avsarogullari
 * @author Yusuf Soysal
 * @since 17 March 2013
 * @version 1.0.0
 *
 */
public class InstanceListenerProcessor implements HazelcastAnnotationProcessor {

    @Override
    public boolean canBeProcessedMoreThanOnce() {
        return false;
    }

    @Override
	public void process(IHazelcastService hazelcastService, Class<?> clazz, Annotation annotation) {

	}

    @Override
    public void process(IHazelcastService hazelcastService, Object obj, Annotation annotation) {

    }
}
