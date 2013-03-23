package com.hazelcast.annotation.processor;

import com.hazelcast.annotation.IExecutorService;
import com.hazelcast.annotation.builder.HazelcastAnnotationProcessor;
import com.hazelcast.annotation.builder.HazelcastFieldAnnotationProcessor;
import com.hazelcast.annotation.configuration.Configuration;
import com.hazelcast.annotation.data.IList;
import com.hazelcast.annotation.data.IQueue;
import com.hazelcast.annotation.data.ISet;
import com.hazelcast.annotation.listener.EntryListener;
import com.hazelcast.annotation.listener.ItemListener;
import com.hazelcast.common.AnnotationField;
import com.hazelcast.common.Annotations;
import com.hazelcast.srv.IHazelcastService;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Date: 23/03/2013 16:40
 * Author Yusuf Soysal
 */
public class HazelcastAwareProcessor implements HazelcastAnnotationProcessor {

    private static Map<Class<?>, List<HazelcastFieldAnnotationProcessor>> processorMap = new ConcurrentHashMap<Class<?>, List<HazelcastFieldAnnotationProcessor>>();

    static {
        init();
    }

    private static void init() {
        loadProcessorMap();
        registerAnnotationsToProcessors();
    }

    private static void loadProcessorMap() {
        for (Annotations.SupportedFieldAnnotation supportedAnnotations : Annotations.SupportedFieldAnnotation.values()) {
            processorMap.put(supportedAnnotations.getClassType(), new ArrayList<HazelcastFieldAnnotationProcessor>());
        }
    }

    private static void registerAnnotationsToProcessors() {
        //Fields
        registerAnnotationToProcessor(IExecutorService.class, new ExecutorServiceProcessor());
        registerAnnotationToProcessor(IQueue.class, new IQueueProcessor());
        registerAnnotationToProcessor(ISet.class, new ISetProcessor());
        registerAnnotationToProcessor(IList.class, new IListProcessor());
    }

    private static void registerAnnotationToProcessor(Class<?> clazz, HazelcastFieldAnnotationProcessor processor) {
        processorMap.get(clazz).add(processor);
    }

    @Override
    public void process(IHazelcastService hazelcastService, Class<?> clazz, Annotation annotation) {
        List<AnnotationField> supportedAnnotationsList = Annotations.SupportedFieldAnnotation.getSupportedAnnotations(clazz);

        if (supportedAnnotationsList.size() > 0) {
            for (AnnotationField supported : supportedAnnotationsList) {
                List<HazelcastFieldAnnotationProcessor> processors = processorMap.get(supported.getSupportedAnnotatiton().getClassType());
                for( HazelcastFieldAnnotationProcessor processor : processors ){
                    processor.process(hazelcastService, clazz, supported.getField(), supported.getAnnotation());
                }
            }
        }
    }
}
