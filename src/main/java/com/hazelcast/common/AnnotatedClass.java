package com.hazelcast.common;

import java.lang.annotation.Annotation;

/**
 * Date: 25/03/2013 10:21
 * Author Yusuf Soysal
 */
public class AnnotatedClass {
    private Annotations.SupportedAnnotation supportedAnnotation;
    private Class<?> clazz;
    private Annotation annotation;

    public AnnotatedClass(Annotations.SupportedAnnotation supportedAnnotation, Class<?> clazz, Annotation annotation) {
        this.supportedAnnotation = supportedAnnotation;
        this.clazz = clazz;
        this.annotation = annotation;
    }

    public Annotations.SupportedAnnotation getSupportedAnnotation() {
        return supportedAnnotation;
    }

    public Class<?> getClazz() {
        return clazz;
    }

    public Annotation getAnnotation() {
        return annotation;
    }
}
