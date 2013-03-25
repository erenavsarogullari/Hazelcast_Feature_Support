package com.hazelcast.annotation.configuration;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Hazelcast Configuration Annotation Interface
 *
 * @author Eren Avsarogullari
 * @author Yusuf Soysal
 * @since 17 March 2013
 * @version 1.0.0
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Configuration {
    String value();
    String file() default "";
    int port() default 0;
    boolean autoIncrement() default true;
    Multicast multicast() default @Multicast(enabled = false);
    TcpIp tcpip() default @TcpIp(enabled = false);
    Aws aws() default @Aws(enabled = false);
    Interfaces interfaces() default @Interfaces(enabled=false, value = {});
}
