/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hazelcast.common;

import com.hazelcast.annotation.ItemListener;
import com.hazelcast.annotation.MembershipListener;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author yusufsoysal
 */
public class Annotations {

    public enum SupportedAnnotation {

        CONFIGURATION(ItemListener.class), 
        ITEM_LISTENER(ItemListener.class), 
        MEMBERSHIP_LISTENER(MembershipListener.class);
        
        private Class<?> clz;

        SupportedAnnotation(Class<?> clz) {
            this.clz = clz;
        }
        
        public Class<?> getClassType(){
            return clz;
        }
        
        public static List<SupportedAnnotation> getSupportedAnnotations(Class<?> clz){
            Annotation[] annotations = clz.getAnnotations();
            List<SupportedAnnotation> supportedAnnotationList = new ArrayList<SupportedAnnotation>();
            
            if( annotations != null ){
                 for( Annotation annotation : annotations ){
                     for( SupportedAnnotation supportedAnnotation : values() ){
                         if( supportedAnnotation.getClassType() == annotation.annotationType() ){
                        	 supportedAnnotationList.add(supportedAnnotation);
                            break;
                         }
                     }
                 }
            }
            
            return supportedAnnotationList;
        }
    };
}
