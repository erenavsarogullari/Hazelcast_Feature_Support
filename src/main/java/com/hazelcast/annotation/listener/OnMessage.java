package com.hazelcast.annotation.listener;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;

/**
 * Hazelcast EntryAdded Annotation Interface
 *
 * @author Eren Avsarogullari
 * @author Yusuf Soysal
 * @since 28 March 2013
 * @version 1.0.0
 *
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface OnMessage {

}
