package com.hazelcast.annotation.configuration;

import com.hazelcast.config.MulticastConfig;

/**
 * Date: 22/03/2013 17:02
 * Author Yusuf Soysal
 */
public @interface Multicast  {
    boolean enabled() default true;
    String group() default MulticastConfig.DEFAULT_MULTICAST_GROUP;
    int port() default MulticastConfig.DEFAULT_MULTICAST_PORT;
    int timeout() default MulticastConfig.DEFAULT_MULTICAST_TIMEOUT_SECONDS;
    int ttl() default MulticastConfig.DEFAULT_MULTICAST_TTL;
    String[] trustedInterfaces() default {};
}
