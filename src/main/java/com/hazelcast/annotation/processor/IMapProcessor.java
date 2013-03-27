package com.hazelcast.annotation.processor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.Set;

import com.hazelcast.annotation.builder.HazelcastFieldAnnotationProcessor;
import com.hazelcast.annotation.data.IMap;
import com.hazelcast.common.HazelcastExtraException;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.srv.IHazelcastService;

/**
 * Hazelcast MultiMap Annotation Processor
 *
 * @author Eren Avsarogullari
 * @author Yusuf Soysal
 * @since 17 March 2013
 * @version 1.0.0
 *
 */
public class IMapProcessor implements HazelcastFieldAnnotationProcessor {

    @Override
    public void process(IHazelcastService hazelcastService, Object obj, Field field, Annotation annotation) {
    	IMap mapAnnotation = (IMap) annotation;

		try {
            /*
             * TODO: call the method below. Instead of looping all instances, just use the instance developer wants to use
             * I'm not changing it right now because probably you already did this :)
             */

			Set<HazelcastInstance> hazelcastInstances = hazelcastService.getAllHazelcastInstances();
			for(HazelcastInstance instance : hazelcastInstances) {
				com.hazelcast.core.IMap map = instance.getMap(mapAnnotation.name());
				field.setAccessible(true);
				field.set(obj, map);
			}						
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} 
	}

    @Override
    public void assignDistributedData(IHazelcastService hazelcastService, Object obj, Field field, String instanceName, String typeName){
        HazelcastInstance hazelcastInstance = Hazelcast.getHazelcastInstanceByName(instanceName);

        com.hazelcast.core.IMap<Object, Object> distributedMap = hazelcastInstance.getMap(typeName);
        if(distributedMap != null) {
            field.setAccessible(true);
            try {
                field.set(obj, distributedMap);
            } catch (IllegalAccessException e) {
                throw new HazelcastExtraException("Cannot access " + obj.getClass().getName() + "'s " + field.getName() + " field", e);
            }
        }
    }
}
