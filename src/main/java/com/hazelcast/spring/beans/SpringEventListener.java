package com.hazelcast.spring.beans;

import com.hazelcast.annotation.HazelcastAware;
import com.hazelcast.annotation.builder.HazelcastAnnotationBuilder;
import com.hazelcast.common.AnnotatedClass;
import com.hazelcast.common.Annotations;
import com.hazelcast.common.HazelcastCommonData;

import com.hazelcast.srv.IHazelcastService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.util.List;

/**
 * Date: 31/03/2013 22:37
 * Author Yusuf Soysal
 */
public class SpringEventListener implements BeanPostProcessor {
    private IHazelcastService hazelcastService;

    public SpringEventListener(){
        hazelcastService = HazelcastAnnotationBuilder.getInstance().getHazelcastService();
    }

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("processing bean " + beanName);

        if( bean.getClass().isAnnotationPresent(HazelcastAware.class) ){
            HazelcastAnnotationBuilder.parseObjectAnnotations(bean);
        }

        // parse hz annotated fields
        List<AnnotatedClass> supportedAnnotatedClassList = Annotations.SupportedAnnotation.getAnnotatedClassList(bean.getClass());

        for (AnnotatedClass supportedAnnotatedClass : supportedAnnotatedClassList) {
            Annotations.SupportedAnnotation supported = supportedAnnotatedClass.getSupportedAnnotation();

            if (supported != Annotations.SupportedAnnotation.CONFIGURATION) {
            	supported.getProcessor().process(hazelcastService, bean, supportedAnnotatedClass.getAnnotation());
                
                break;
            }
        }

        return bean;
    }
}
