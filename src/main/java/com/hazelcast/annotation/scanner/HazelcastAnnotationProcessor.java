/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hazelcast.annotation.scanner;

import java.lang.annotation.Annotation;

/**
 *
 * @author yusufsoysal
 */
public interface HazelcastAnnotationProcessor {
    
    void process(Class<?> clazz, Annotation annotation);
    
}
