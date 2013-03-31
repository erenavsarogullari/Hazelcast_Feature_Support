package com.hazelcast.spring.beans;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class ApplicationContextListener implements ApplicationContextAware {

    private ApplicationContext applicationContext;
    private static ApplicationContextListener INSTANCE;

    public ApplicationContextListener() {
        INSTANCE = this;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;

    }

    public ApplicationContext getApplicationContext() {
        return applicationContext;
    }

    public static ApplicationContextListener getINSTANCE() {
        return INSTANCE;
    }

}
