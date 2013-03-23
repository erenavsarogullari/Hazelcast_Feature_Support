package com.hazelcast.annotation;

/**
 * Date: 22/03/2013 17:43
 * Author Yusuf Soysal
 */
public @interface TcpIp {
    boolean enabled() default true;
    int timeout() default -1;
    String requiredMember() default "";
    String[] members() default {};
}
