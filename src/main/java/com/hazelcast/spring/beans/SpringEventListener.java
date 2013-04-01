package com.hazelcast.spring.beans;

import com.hazelcast.annotation.builder.HazelcastAnnotationBuilder;
import com.hazelcast.common.AnnotatedClass;
import com.hazelcast.common.Annotations;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

import java.util.List;

/**
 * Date: 31/03/2013 22:37
 * Author Yusuf Soysal
 */
public class SpringEventListener implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("processing bean " + beanName);
        // parse hz annotated fields
        List<AnnotatedClass> supportedAnnotatedClassList = Annotations.SupportedAnnotation.getAnnotatedClassList(bean.getClass());

        for (AnnotatedClass supportedAnnotatedClass : supportedAnnotatedClassList) {
            Annotations.SupportedAnnotation supported = supportedAnnotatedClass.getSupportedAnnotation();
            if (supported != Annotations.SupportedAnnotation.CONFIGURATION) {
                HazelcastAnnotationBuilder.parseObjectAnnotations(bean);
            }
        }

        return bean;
    }
}
