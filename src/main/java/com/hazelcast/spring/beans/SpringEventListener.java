package com.hazelcast.spring.beans;

import com.hazelcast.annotation.HazelcastAware;
import com.hazelcast.annotation.builder.HazelcastAnnotationBuilder;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;

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
        System.out.println("processing bean " + beanName );
        // parse hz annotated fields
        if( bean.getClass().isAnnotationPresent(HazelcastAware.class) ){
            HazelcastAnnotationBuilder.parseObjectAnnotations(bean);
        }

        return bean;
    }
}
