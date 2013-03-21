package com.hazelcast.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import com.hazelcast.common.EntryTypeEnum;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface EntryListener {

	EntryTypeEnum[] type();
	String[] distributedObjectName();
	boolean needsValue() default false;

}
