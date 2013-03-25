package com.hazelcast.common;

import com.hazelcast.annotation.HazelcastAware;
import com.hazelcast.annotation.builder.HazelcastAnnotationProcessor;
import com.hazelcast.annotation.configuration.Configuration;
import com.hazelcast.annotation.data.HZInstance;
import com.hazelcast.annotation.listener.EntryListener;
import com.hazelcast.annotation.IExecutorService;
import com.hazelcast.annotation.data.IList;
import com.hazelcast.annotation.data.IMap;
import com.hazelcast.annotation.data.IQueue;
import com.hazelcast.annotation.data.ISet;
import com.hazelcast.annotation.data.MultiMap;
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
 * @since 17 March 2013
 * @version 1.0.0
 *
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

        public Class<?> getClassType(){
            return clz;
        }

        public static List<AnnotatedClass> getSupportedAnnotations(Class<?> clz){
            Annotation[] clzAnnotations = clz.getAnnotations();
            List<AnnotatedClass> supportedAnnotationList = new ArrayList<AnnotatedClass>();

            if( clzAnnotations != null ){
                for( Annotation annotation : clzAnnotations ){
                    for( SupportedAnnotation supportedAnnotation : SupportedAnnotation.values() ){
                        if( supportedAnnotation.getClassType() == annotation.annotationType() ){
                            supportedAnnotationList.add(new AnnotatedClass(supportedAnnotation, clz, annotation));
                            break;
                        }
                    }
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

        public Class<?> getClassType(){
            return clz;
        }

        public static List<AnnotatedField> getSupportedAnnotations(Class<?> clz){
            List<AnnotatedField> supportedAnnotationList = new ArrayList<AnnotatedField>();

            Field[] fields = clz.getDeclaredFields();
            if( fields.length > 0){
                for(Field field : fields) {
                    for( Annotation annotation : field.getDeclaredAnnotations() ){
                        for( SupportedFieldAnnotation supportedAnnotation : SupportedFieldAnnotation.values() ){
                            if( supportedAnnotation.getClassType() == annotation.annotationType() ){
                                supportedAnnotationList.add(new AnnotatedField(supportedAnnotation, field, annotation));
                                break;
                            }
                        }
                    }

                }
            }

            return supportedAnnotationList;
        }
    }
}
