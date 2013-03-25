package com.hazelcast.annotation.data;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.hazelcast.common.EvictionPolicyEnum;

/**
 * Hazelcast MultiMap Annotation Interface
 *
 * @author Eren Avsarogullari
 * @author Yusuf Soysal
 * @since 24 March 2013
 * @version 1.0.0
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface IMap {

	String name();
	int backupCount() default 1;
	int asyncBackupCount() default 1;
	int timeToLiveSeconds() default 0;
	int maxIdleSeconds() default 0;
	EvictionPolicyEnum evictionPolicy() default EvictionPolicyEnum.NONE;
	
}
