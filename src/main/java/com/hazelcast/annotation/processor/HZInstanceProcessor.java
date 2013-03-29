package com.hazelcast.annotation.processor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

import com.hazelcast.annotation.builder.HazelcastFieldAnnotationProcessor;
import com.hazelcast.annotation.data.HZInstance;
import com.hazelcast.common.HazelcastExtraException;
import com.hazelcast.config.Config;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.srv.IHazelcastService;

/**
 * Date: 23/03/2013 17:39
 * Author Yusuf Soysal
 */
public class HZInstanceProcessor implements HazelcastFieldAnnotationProcessor {
    @Override
    public void process(IHazelcastService hazelcastService, Object obj, Field field, Annotation annotation) {
        HZInstance hzInstanceAnnotation = (HZInstance) annotation;

        HazelcastInstance hazelcastInstance = hazelcastService.getHazelcastInstanceByName(hzInstanceAnnotation.value());
    	
        if(hazelcastInstance == null) {
        	Config cfg = new Config();
        	cfg.setInstanceName(hzInstanceAnnotation.value());
        	hazelcastInstance = hazelcastService.newHazelcastInstance(cfg);
        }        
        
        try {
            field.setAccessible(true);
            field.set(obj, hazelcastInstance);

        } catch (IllegalArgumentException e) {
            throw new HazelcastExtraException("Cannot set value to  " + obj.getClass().getName() + "'s " + field.getName() + " field", e);
        } catch (IllegalAccessException e) {
            throw new HazelcastExtraException("Cannot access " + obj.getClass().getName() + "'s " + field.getName() + " field", e);
        }
    }

    @Override
    public void assignDistributedData(IHazelcastService hazelcastService, Object obj, Field field, String instanceName, String typeName){
        // nothing to do here
    }
}
