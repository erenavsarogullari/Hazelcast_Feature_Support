package com.hazelcast.annotation.processor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Set;
import java.util.concurrent.ExecutorService;

import com.hazelcast.annotation.IExecutorService;
import com.hazelcast.annotation.builder.HazelcastAnnotationProcessor;
import com.hazelcast.annotation.builder.HazelcastFieldAnnotationProcessor;
import com.hazelcast.common.HazelcastExtraException;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.srv.IHazelcastService;
import sun.org.mozilla.javascript.internal.NativeObject;

/**
 * Hazelcast EntryListener Annotation Processor
 *
 * @author Eren Avsarogullari
 * @author Yusuf Soysal
 * @since 17 March 2013
 * @version 1.0.0
 *
 */
public class ExecutorServiceProcessor implements HazelcastFieldAnnotationProcessor {

    @Override
    public void process(IHazelcastService hazelcastService, Object obj, Field field, Annotation annotation) {
        IExecutorService executorAnnotation = (IExecutorService) annotation;

        try {
            /*
             * TODO: call the method below. Instead of looping all instances, just use the instance developer wants to use
             * I'm not changing it right now because probably you already did this :)
             */

            Set<HazelcastInstance> hazelcastInstances = hazelcastService.getAllHazelcastInstances();
            for (HazelcastInstance hazelcastInstance : hazelcastInstances) {
                // hazelcast 3 ile executor service'e isim vermek zorunlu diye biliyorum
                java.util.concurrent.ExecutorService executorService = (executorAnnotation.name() != null) ? hazelcastInstance.getExecutorService(executorAnnotation.name()) : hazelcastInstance.getExecutorService();
                field.setAccessible(true);
                field.set(obj, executorService);
            }

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
