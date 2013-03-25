package com.hazelcast.common;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

/**
 * Date: 23/03/2013 16:58
 * Author Yusuf Soysal
 */
public class AnnotatedField {
    private Annotations.SupportedFieldAnnotation supportedAnnotatiton;

    private Field field;

    private Annotation annotation;

    public AnnotatedField(Annotations.SupportedFieldAnnotation supportedAnnotatiton, Field field, Annotation annotation) {
        this.supportedAnnotatiton = supportedAnnotatiton;
        this.field = field;
        this.annotation = annotation;
    }

    public Field getField() {
        return field;
    }

    public Annotations.SupportedFieldAnnotation getSupportedAnnotatiton() {
        return supportedAnnotatiton;
    }

    public Annotation getAnnotation() {
        return annotation;
    }
}
