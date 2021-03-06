package com.hazelcast.annotation.configuration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Date: 23/03/2013 15:30
 * Author Yusuf Soysal
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Interfaces {
    boolean enabled() default true;
    String[] value();
}
