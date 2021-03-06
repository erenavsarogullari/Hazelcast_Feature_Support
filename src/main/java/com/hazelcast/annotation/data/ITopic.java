package com.hazelcast.annotation.data;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.hazelcast.common.SystemConstants;

/**
 * Hazelcast ITopic Annotation Interface
 *
 * @author Eren Avsarogullari
 * @author Yusuf Soysal
 * @since 25 March 2013
 * @version 1.0.0
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface ITopic {

	String instanceName() default "";
	String name() default SystemConstants.DEFAULT;
	
}
