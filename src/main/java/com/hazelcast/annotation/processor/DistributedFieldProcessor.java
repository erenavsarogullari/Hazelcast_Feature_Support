package com.hazelcast.annotation.processor;

import com.hazelcast.annotation.builder.HazelcastFieldAnnotationProcessor;
import com.hazelcast.annotation.data.*;
import com.hazelcast.annotation.data.IList;
import com.hazelcast.annotation.data.IMap;
import com.hazelcast.annotation.data.IQueue;
import com.hazelcast.annotation.data.ISet;
import com.hazelcast.common.Annotations;
import com.hazelcast.srv.IHazelcastService;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;
import java.util.Queue;
import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Date: 26/03/2013 21:56
 * Author Yusuf Soysal
 */
public class DistributedFieldProcessor implements HazelcastFieldAnnotationProcessor {

    private Map<Class<?>, Class<?>> distributedClassMap = new ConcurrentHashMap<Class<?>, Class<?>>();

    public DistributedFieldProcessor(){
        distributedClassMap.put(com.hazelcast.core.IMap.class, IMap.class);
        distributedClassMap.put(Map.class, IMap.class);
        distributedClassMap.put(ConcurrentMap.class, IMap.class);
        distributedClassMap.put(com.hazelcast.core.ISet.class, ISet.class);
        distributedClassMap.put(Set.class, ISet.class);
        distributedClassMap.put(com.hazelcast.core.IQueue.class, IQueue.class);
        distributedClassMap.put(Queue.class, IQueue.class);
        distributedClassMap.put(BlockingQueue.class, IQueue.class);
        distributedClassMap.put(com.hazelcast.core.IList.class, IList.class);
        distributedClassMap.put(List.class, IList.class);
    }

    @Override
    public void process(IHazelcastService hazelcastService, Object obj, Field field, Annotation annotation) {
        Distributed distributedAnnotation = (Distributed) annotation;

        Class<?> type = field.getType();

        Class<?> hazelcastClass = distributedClassMap.get(type);

        if( hazelcastClass != null ){
            Annotations.SupportedFieldAnnotation supportedAnnotation = Annotations.SupportedFieldAnnotation.findByClassType(hazelcastClass);
            if( supportedAnnotation != null ){
                supportedAnnotation.getProcessor().assignDistributedData(hazelcastService, obj, field, distributedAnnotation.instanceName(), distributedAnnotation.name() );
            }
        }
    }

    @Override
    public void assignDistributedData(IHazelcastService hazelcastService, Object obj, Field field, String instanceName, String typeName) {
        // nothing to do here
    }
}
