package com.hazelcast.common;

import com.hazelcast.annotation.Configuration;
import com.hazelcast.annotation.ExecutorService;
import com.hazelcast.annotation.ItemListener;
import com.hazelcast.annotation.MembershipListener;
import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author yusufsoysal
 */
public class Annotations {

    public enum SupportedAnnotation {
    	
        CONFIGURATION(Configuration.class), 
        ITEM_LISTENER(ItemListener.class), 
        MEMBERSHIP_LISTENER(MembershipListener.class),
        EXECUTOR_SERVICE(ExecutorService.class);
        
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
