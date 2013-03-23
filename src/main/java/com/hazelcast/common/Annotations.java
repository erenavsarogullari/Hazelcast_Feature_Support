package com.hazelcast.common;

import com.hazelcast.annotation.HazelcastAware;
import com.hazelcast.annotation.configuration.Configuration;
import com.hazelcast.annotation.data.HZInstance;
import com.hazelcast.annotation.listener.EntryListener;
import com.hazelcast.annotation.IExecutorService;
import com.hazelcast.annotation.data.IList;
import com.hazelcast.annotation.data.IQueue;
import com.hazelcast.annotation.data.ISet;
import com.hazelcast.annotation.listener.ItemListener;
import com.hazelcast.annotation.listener.MembershipListener;

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

        CONFIGURATION(Configuration.class),
        HAZELCASTAWAR(HazelcastAware.class),
        ITEM_LISTENER(ItemListener.class),
        ENTRY_LISTENER(EntryListener.class),
        MEMBERSHIP_LISTENER(MembershipListener.class);

        private Class<?> clz;

        SupportedAnnotation(Class<?> clz) {
            this.clz = clz;
        }

        public Class<?> getClassType(){
            return clz;
        }

        public static List<SupportedAnnotation> getSupportedAnnotations(Class<?> clz){
            Annotation[] clzAnnotations = clz.getAnnotations();
            List<SupportedAnnotation> supportedAnnotationList = new ArrayList<SupportedAnnotation>();

            if( clzAnnotations != null ){
                addSupportedAnnotationToList(clzAnnotations, supportedAnnotationList);
            }

            Field[] fields = clz.getDeclaredFields();
            if( fields.length > 0){
                for(Field field : fields) {
                    addSupportedAnnotationToList(field.getDeclaredAnnotations(), supportedAnnotationList);
                }
            }

            return supportedAnnotationList;
        }
    }

    public enum SupportedFieldAnnotation {

        HZ_INSTANCE(HZInstance.class),
        EXECUTOR_SERVICE(IExecutorService.class),
        IQUEUE(IQueue.class),
        ISET(ISet.class),
        ILIST(IList.class);

        private Class<?> clz;

        SupportedFieldAnnotation(Class<?> clz) {
            this.clz = clz;
        }

        public Class<?> getClassType(){
            return clz;
        }

        public static List<AnnotationField> getSupportedAnnotations(Class<?> clz){
            List<AnnotationField> supportedAnnotationList = new ArrayList<AnnotationField>();

            Field[] fields = clz.getDeclaredFields();
            if( fields.length > 0){
                for(Field field : fields) {
                    for( Annotation annotation : field.getDeclaredAnnotations() ){
                        for( SupportedFieldAnnotation supportedAnnotation : SupportedFieldAnnotation.values() ){
                            if( supportedAnnotation.getClassType() == annotation.annotationType() ){
                                supportedAnnotationList.add(new AnnotationField(supportedAnnotation, field, annotation));
                                break;
                            }
                        }
                    }

                }
            }

            return supportedAnnotationList;
        }
    }
    
    private static void addSupportedAnnotationToList(Annotation[] annotations, List<SupportedAnnotation> supportedAnnotationList) {
    	for( Annotation annotation : annotations ){
            for( SupportedAnnotation supportedAnnotation : SupportedAnnotation.values() ){
                if( supportedAnnotation.getClassType() == annotation.annotationType() ){
               	 	supportedAnnotationList.add(supportedAnnotation);
                    break;
                }
            }
        }
    }
}
