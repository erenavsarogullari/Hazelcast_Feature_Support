package com.hazelcast.annotation.listener;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.hazelcast.common.ItemTypeEnum;

/**
 * Hazelcast ItemListener Annotation Interface
 *
 * @author Eren Avsarogullari
 * @author Yusuf Soysal
 * @since 17 March 2013
 * @version 1.0.0
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface ItemListener {

	ItemTypeEnum[] type();
	String[] distributedObjectName();
	boolean needsValue() default false;
	
}