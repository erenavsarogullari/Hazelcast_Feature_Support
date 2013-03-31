package com.hazelcast.annotation.data;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.hazelcast.common.SystemConstants;

/**
 * Hazelcast IQueue Annotation Interface
 *
 * @author Eren Avsarogullari
 * @author Yusuf Soysal
 * @since 17 March 2013
 * @version 1.0.0
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface IQueue {

	String instanceName() default "";
	String name();
	int maxSizePerJvm() default 0;
	String backingMapRef() default SystemConstants.DEFAULT;
	
}
