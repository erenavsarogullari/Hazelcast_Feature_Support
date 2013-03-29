package com.hazelcast.annotation.processor;

import com.hazelcast.annotation.builder.HZAware;
import com.hazelcast.annotation.builder.HazelcastAnnotationProcessor;
import com.hazelcast.annotation.listener.*;
import com.hazelcast.common.EntryTypeEnum;
import com.hazelcast.common.HazelcastExtraException;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.core.MultiMap;
import com.hazelcast.listener.proxy.EntryListenerProxy;
import com.hazelcast.srv.IHazelcastService;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;

/**
 * Hazelcast EntryListener Annotation Processor
 *
 * @author Eren Avsarogullari
 * @author Yusuf Soysal
 * @version 1.0.0
 * @since 17 March 2013
 */
public class EntryListenerProcessor implements HazelcastAnnotationProcessor {


    @Override
    public boolean canBeProcessedMoreThanOnce() {
        return false;
    }

    @Override
    public void process(IHazelcastService hazelcastService, Object obj, Annotation annotation) {
        createEntryProcessor(hazelcastService, obj.getClass(), obj, annotation);
    }

    @Override
    public void process(IHazelcastService hazelcastService, Class<?> clazz, Annotation annotation) {
        createEntryProcessor(hazelcastService, clazz, null, annotation);

    }

    public void createEntryProcessor(IHazelcastService hazelcastService, Class<?> clazz, Object obj, Annotation annotation) {
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
            addEntryListener(hazelcastService, clazz, obj, annotation, entryAdded, entryRemoved, entryUpdated, entryEvicted);
        }
    }

    private void addEntryListener(IHazelcastService hazelcastService, Class<?> clazz, Object obj, Annotation annotation, Method entryAdded, Method entryRemoved, Method entryUpdated, Method entryEvicted) {
        String[] distributedObjectNames = null;
        EntryListenerProxy entryListenerProxy = null;

        if (obj == null) {
            obj = HZAware.initialize(clazz);
        }


        Set<HazelcastInstance> allHazelcastInstances = hazelcastService.getAllHazelcastInstances();

        EntryListener listener = (EntryListener) annotation;

        EntryTypeEnum[] types = listener.type();

        for (HazelcastInstance instance : allHazelcastInstances) {

            for (EntryTypeEnum type : types) {

                entryListenerProxy = new EntryListenerProxy(obj, entryAdded, entryRemoved, entryUpdated, entryEvicted);

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
