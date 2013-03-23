package com.hazelcast.annotation.configuration;

/**
 * Date: 23/03/2013 15:30
 * Author Yusuf Soysal
 */
public @interface Interfaces {
    boolean enabled() default true;
    String[] value();
}
