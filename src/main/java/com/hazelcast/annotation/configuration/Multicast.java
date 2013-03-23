package com.hazelcast.annotation.configuration;

import com.hazelcast.config.MulticastConfig;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Date: 22/03/2013 17:02
 * Author Yusuf Soysal
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Multicast  {
    boolean enabled() default true;
    String group() default MulticastConfig.DEFAULT_MULTICAST_GROUP;
    int port() default MulticastConfig.DEFAULT_MULTICAST_PORT;
    int timeout() default MulticastConfig.DEFAULT_MULTICAST_TIMEOUT_SECONDS;
    int ttl() default MulticastConfig.DEFAULT_MULTICAST_TTL;
    String[] trustedInterfaces() default {};
}
