package com.hazelcast.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.hazelcast.common.SystemConstants;

/**
 * Hazelcast IExecutorService Annotation Interface
 *
 * @author Eren Avsarogullari
 * @author Yusuf Soysal
 * @since 17 March 2013
 * @version 1.0.0
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface IExecutorService {

	String name();
	int corePoolSize() default 16;
	int maxPoolSize() default 64;
	int keepAliveSeconds() default 60;
	
}
