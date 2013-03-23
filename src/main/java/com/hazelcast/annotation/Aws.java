package com.hazelcast.annotation;

/**
 * Date: 23/03/2013 13:58
 * Author Yusuf Soysal
 */
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
