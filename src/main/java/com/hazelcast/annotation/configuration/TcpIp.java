package com.hazelcast.annotation.configuration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Date: 22/03/2013 17:43
 * Author Yusuf Soysal
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface TcpIp {
    boolean enabled() default true;
    int timeout() default -1;
    String requiredMember() default "";
    String[] members() default {};
}
