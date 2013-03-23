package com.hazelcast.annotation.configuration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Date: 23/03/2013 13:58
 * Author Yusuf Soysal
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Aws {
    boolean enabled() default true;
    String accessKey() default "";
    String secretKey() default "";
    String region() default "";
    String group() default "";
    String tagKey() default "";
    String tagValue() default "";
    String hostHeader() default "";
}
