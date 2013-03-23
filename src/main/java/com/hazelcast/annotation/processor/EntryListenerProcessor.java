package com.hazelcast.annotation.processor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;

import com.hazelcast.annotation.EntryAdded;
import com.hazelcast.annotation.EntryEvicted;
import com.hazelcast.annotation.EntryListener;
import com.hazelcast.annotation.EntryRemoved;
import com.hazelcast.annotation.EntryUpdated;
import com.hazelcast.annotation.scanner.HazelcastAnnotationProcessor;
import com.hazelcast.common.EntryTypeEnum;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.core.MultiMap;
import com.hazelcast.listener.proxy.EntryListenerProxy;

public class EntryListenerProcessor implements HazelcastAnnotationProcessor {

	@Override
	public void process(Class<?> clazz, Annotation annotation) {
		Method entryAdded = null, entryRemoved = null, entryUpdated = null, entryEvicted = null;
		int numberOfMethods = 0;

		for (Method method : clazz.getMethods()) {
			Annotation[] annotations = method.getAnnotations();
			for (Annotation tempAnnotation : annotations) {
				if (tempAnnotation.annotationType() == EntryListenerEnum.ENTRY_ADDED.getEntryType()) {
					entryAdded = method;
					++numberOfMethods;
				} else if (tempAnnotation.annotationType() == EntryListenerEnum.ENTRY_REMOVED.getEntryType()) {
					entryRemoved = method;
					++numberOfMethods;
				} else if (tempAnnotation.annotationType() == EntryListenerEnum.ENTRY_UPDATED.getEntryType()) {
					entryUpdated = method;
					++numberOfMethods;
				} else if (tempAnnotation.annotationType() == EntryListenerEnum.ENTRY_EVICTED.getEntryType()) {
					entryEvicted = method;
					++numberOfMethods;
				}
			}
		}

		if (numberOfMethods > 0) {
			addEntryListener(clazz, annotation, entryAdded, entryRemoved, entryUpdated, entryEvicted);
		}

	}
	
	private void addEntryListener(Class<?> clazz, Annotation annotation, Method entryAdded, Method entryRemoved, Method entryUpdated, Method entryEvicted) {
		String[] distributedObjectNames = null;
		EntryListenerProxy entryListenerProxy = null;
		try {
			Set<HazelcastInstance> allHazelcastInstances = Hazelcast.getAllHazelcastInstances();
			
			EntryListener listener = (EntryListener) annotation;
			
			EntryTypeEnum[] types = listener.type();
			
			for (HazelcastInstance instance : allHazelcastInstances) {			
				
				for (EntryTypeEnum type : types) {
					
					entryListenerProxy = new EntryListenerProxy(clazz.newInstance(), entryAdded, entryRemoved, entryUpdated, entryEvicted);
					
					switch (type) {

					case MAP:
						distributedObjectNames = listener.distributedObjectName();
						for (String distributedObjectName : distributedObjectNames) {
							IMap map = instance.getMap(distributedObjectName);
							if (map != null) {
								map.addEntryListener(entryListenerProxy, listener.needsValue());
							}
						}
						
						break;

					case MULTI_MAP:
						distributedObjectNames = listener.distributedObjectName();
						for (String distributedObjectName : distributedObjectNames) {
						MultiMap multiMap = instance.getMultiMap(distributedObjectName);
							if (multiMap != null) {
								multiMap.addEntryListener(entryListenerProxy, listener.needsValue());
							}
						}
						break;
					}
				}				
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	private enum EntryListenerEnum {
		
		ENTRY_ADDED(EntryAdded.class), 
		ENTRY_REMOVED(EntryRemoved.class),
		ENTRY_UPDATED(EntryUpdated.class), 
		ENTRY_EVICTED(EntryEvicted.class);

		private Class<?> entryListenerType;

		private EntryListenerEnum(Class<?> entryListenerType) {
			this.entryListenerType = entryListenerType;
		}

		public Class<?> getEntryType() {
			return entryListenerType;
		}
		
	}

}
