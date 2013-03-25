package com.hazelcast.common;

import com.hazelcast.annotation.HazelcastAware;
import com.hazelcast.annotation.IExecutorService;
import com.hazelcast.annotation.builder.HazelcastAnnotationProcessor;
import com.hazelcast.annotation.configuration.Configuration;
import com.hazelcast.annotation.data.*;
import com.hazelcast.annotation.listener.EntryListener;
import com.hazelcast.annotation.listener.ItemListener;
import com.hazelcast.annotation.listener.MembershipListener;
import com.hazelcast.annotation.processor.*;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * Hazelcast Annotations Class
 *
 * @author Yusuf Soysal
 * @author Eren Avsarogullari
 * @version 1.0.0
 * @since 17 March 2013
 */
public class Annotations {

    public enum SupportedAnnotation {

        CONFIGURATION(Configuration.class, new ConfigurationProcessor()),
        HAZELCASTAWAR(HazelcastAware.class, new HazelcastAwareProcessor()),
        ITEM_LISTENER(ItemListener.class, new ItemListenerProcessor()),
        ENTRY_LISTENER(EntryListener.class, new EntryListenerProcessor()),
        MEMBERSHIP_LISTENER(MembershipListener.class, new MembershipListenerProcessor());

        private Class<?> clz;
        private HazelcastAnnotationProcessor processor;

        SupportedAnnotation(Class<?> clz, HazelcastAnnotationProcessor processor) {
            this.clz = clz;
            this.processor = processor;
        }

        public Class<?> getClassType() {
            return clz;
        }

        public static List<AnnotatedClass> getSupportedAnnotations(Class<?> clz) {
            Annotation[] clzAnnotations = clz.getAnnotations();
            List<AnnotatedClass> supportedAnnotationList = new ArrayList<AnnotatedClass>();

            for (Annotation annotation : clzAnnotations) {
                AnnotatedClass annotatedClass = visit(clz, annotation);
                if( annotatedClass != null ){
                    supportedAnnotationList.add(annotatedClass);
                }
            }

            return supportedAnnotationList;
        }

        public HazelcastAnnotationProcessor getProcessor() {
            return processor;
        }
    }

    public enum SupportedFieldAnnotation {

        HZ_INSTANCE(HZInstance.class),
        EXECUTOR_SERVICE(IExecutorService.class),
        IQUEUE(IQueue.class),
        ISET(ISet.class),
        ILIST(IList.class),
        IMAP(IMap.class),
        MULTI_MAP(MultiMap.class);

        private Class<?> clz;

        SupportedFieldAnnotation(Class<?> clz) {
            this.clz = clz;
        }

        public Class<?> getClassType() {
            return clz;
        }

        public static List<AnnotatedField> getSupportedAnnotations(Class<?> clz) {
            List<AnnotatedField> supportedAnnotationList = new ArrayList<AnnotatedField>();

            Field[] fields = clz.getDeclaredFields();
            for (Field field : fields) {
                supportedAnnotationList.addAll(visit(field));
            }

            return supportedAnnotationList;
        }
    }

    private static List<AnnotatedField> visit(Field field) {
        List<AnnotatedField> supportedAnnotationList = new ArrayList<AnnotatedField>();

        for (Annotation annotation : field.getDeclaredAnnotations()) {
            AnnotatedField annotated = visit(field, annotation);
            if (annotated != null) {
                supportedAnnotationList.add(annotated);
            }
        }

        return supportedAnnotationList;
    }

    private static AnnotatedField visit(Field field, Annotation annotation) {
        AnnotatedField annotated = null;
        for (SupportedFieldAnnotation supportedAnnotation : SupportedFieldAnnotation.values()) {
            if (supportedAnnotation.getClassType() == annotation.annotationType()) {
                annotated = new AnnotatedField(supportedAnnotation, field, annotation);
                break;
            }
        }

        return annotated;
    }

    private static AnnotatedClass visit(Class<?> clz, Annotation annotation){
        AnnotatedClass annotatedClass = null;
        for (SupportedAnnotation supportedAnnotation : SupportedAnnotation.values()) {
            if (supportedAnnotation.getClassType() == annotation.annotationType()) {
                annotatedClass = new AnnotatedClass(supportedAnnotation, clz, annotation);
                break;
            }
        }

        return annotatedClass;
    }
}
