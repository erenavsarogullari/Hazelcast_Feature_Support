package com.hazelcast.common;

import com.hazelcast.annotation.HazelcastAware;
import com.hazelcast.annotation.IExecutorService;
import com.hazelcast.annotation.builder.HazelcastAnnotationProcessor;
import com.hazelcast.annotation.builder.HazelcastFieldAnnotationProcessor;
import com.hazelcast.annotation.configuration.Configuration;
import com.hazelcast.annotation.data.*;
import com.hazelcast.annotation.listener.EntryListener;
import com.hazelcast.annotation.listener.ItemListener;
import com.hazelcast.annotation.listener.MembershipListener;
import com.hazelcast.annotation.listener.MessageListener;
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
        HAZELCASTAWARE(HazelcastAware.class, new HazelcastAwareProcessor()),
        ITEM_LISTENER(ItemListener.class, new ItemListenerProcessor()),
        ENTRY_LISTENER(EntryListener.class, new EntryListenerProcessor()),
        MEMBERSHIP_LISTENER(MembershipListener.class, new MembershipListenerProcessor()),
        MESSAGE_LISTENER(MessageListener.class, new MessageListenerProcessor());

        private Class<?> clz;
        private HazelcastAnnotationProcessor processor;

        SupportedAnnotation(Class<?> clz, HazelcastAnnotationProcessor processor) {
            this.clz = clz;
            this.processor = processor;
        }

        public Class<?> getClassType() {
            return clz;
        }

        public static List<AnnotatedClass> getAnnotatedClassList(Class<?> clz) {
            Annotation[] clzAnnotations = clz.getAnnotations();
            List<AnnotatedClass> annotatedClassList = new ArrayList<AnnotatedClass>();

            for (Annotation annotation : clzAnnotations) {
                AnnotatedClass annotatedClass = visit(clz, annotation);
                if( annotatedClass != null ){
                	annotatedClassList.add(annotatedClass);
                }
            }

            return annotatedClassList;
        }

        public HazelcastAnnotationProcessor getProcessor() {
            return processor;
        }
    }

    public enum SupportedFieldAnnotation {

        HZ_INSTANCE(HZInstance.class, new HZInstanceProcessor()),
        EXECUTOR_SERVICE(IExecutorService.class, new ExecutorServiceProcessor()),
        IQUEUE(IQueue.class, new IQueueProcessor()),
        ISET(ISet.class, new ISetProcessor()),
        ILIST(IList.class, new IListProcessor()),
        IMAP(IMap.class, new IMapProcessor()),
        MULTI_MAP(MultiMap.class, new MultiMapProcessor()),
        DISTRIBUTED(Distributed.class, new DistributedFieldProcessor()),
        ITOPIC(ITopic.class, new ITopicProcessor());

        private Class<?> clz;
        private HazelcastFieldAnnotationProcessor processor;

        SupportedFieldAnnotation(Class<?> clz, HazelcastFieldAnnotationProcessor processor) {
            this.clz = clz;
            this.processor = processor;
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

            // lets have a look at parent class
            if( clz.getSuperclass() != Object.class){
                supportedAnnotationList.addAll(getSupportedAnnotations(clz.getSuperclass()));
            }

            return supportedAnnotationList;
        }

        public static SupportedFieldAnnotation findByClassType(Class<?> clazz){
            for (SupportedFieldAnnotation annotation : values()) {
                if( annotation.getClassType() == clazz ){
                    return annotation;
                }
            }

            return null;
        }

        public HazelcastFieldAnnotationProcessor getProcessor() {
            return processor;
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
