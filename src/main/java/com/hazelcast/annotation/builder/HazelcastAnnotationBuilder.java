package com.hazelcast.annotation.builder;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

import com.hazelcast.annotation.HazelcastAware;
import com.hazelcast.annotation.configuration.Configuration;
import com.hazelcast.annotation.listener.EntryListener;
import com.hazelcast.annotation.IExecutorService;
import com.hazelcast.annotation.data.IList;
import com.hazelcast.annotation.data.IQueue;
import com.hazelcast.annotation.data.ISet;
import com.hazelcast.annotation.listener.ItemListener;
import com.hazelcast.annotation.processor.*;
import com.hazelcast.common.Annotations;
import com.hazelcast.common.ClasspathScanEventListener;
import com.hazelcast.common.ClasspathScanner;
import com.hazelcast.common.SystemConstants;
import com.hazelcast.srv.HazelcastService;
import com.hazelcast.srv.IHazelcastService;

/**
 * Hazelcast Annotation Builder
 *
 * @author Yusuf Soysal
 * @author Eren Avsarogullari
 * @since 17 March 2013
 * @version 1.0.0
 *
 */
public class HazelcastAnnotationBuilder {

    private static Map<Class<?>, List<HazelcastAnnotationProcessor>> processorMap = new ConcurrentHashMap<Class<?>, List<HazelcastAnnotationProcessor>>();
    private static Map<Annotations.SupportedAnnotation, List<Class<?>>> classMap = new ConcurrentHashMap<Annotations.SupportedAnnotation, List<Class<?>>>();
    private static IHazelcastService hazelcastService = new HazelcastService();
    
    static {
        init();
    }

    private static void init() {
        loadProcessorMap();        
        registerAnnotationsToProcessors();
    }

	private static void loadProcessorMap() {
		for (Annotations.SupportedAnnotation supportedAnnotations : Annotations.SupportedAnnotation.values()) {
            processorMap.put(supportedAnnotations.getClassType(), new ArrayList<HazelcastAnnotationProcessor>());
        }
	}
	
	private static void registerAnnotationsToProcessors() {
		//Types
        registerAnnotationToProcessor(Configuration.class, new ConfigurationProcessor());
		registerAnnotationToProcessor(ItemListener.class, new ItemListenerProcessor());
		registerAnnotationToProcessor(EntryListener.class, new EntryListenerProcessor());
        registerAnnotationToProcessor(HazelcastAware.class, new HazelcastAwareProcessor());
	}

    private static void registerAnnotationToProcessor(Class<?> clazz, HazelcastAnnotationProcessor processor) {
        processorMap.get(clazz).add(processor);
    }
    
    private static void fireEvents() {
    	
    	for (Annotations.SupportedAnnotation supportedAnnotation : Annotations.SupportedAnnotation.values()) {
            List<Class<?>> clazzListOfSupportedAnnotation = classMap.get(supportedAnnotation);

            if (clazzListOfSupportedAnnotation != null) {
                for (Class<?> clazz : clazzListOfSupportedAnnotation) {
                    List<HazelcastAnnotationProcessor> processorListOfSupportedAnnotation = processorMap.get(supportedAnnotation.getClassType());
                    for (HazelcastAnnotationProcessor processor : processorListOfSupportedAnnotation) {
                        processor.process(hazelcastService, clazz, clazz.getAnnotation((Class)supportedAnnotation.getClassType()));
                    }
                }
            }
        }
    }
    
    public static void build(String packageName) {
        ClasspathScanner.scanClasspathForPackage(packageName, new ClasspathScannerImpl());
        fireEvents();
    }

    public static class ClasspathScannerImpl implements ClasspathScanEventListener{

        @Override
        public void classFound(Class<?> clazz) {
            List<Annotations.SupportedAnnotation> supportedAnnotationsList = Annotations.SupportedAnnotation.getSupportedAnnotations(clazz);
            if (supportedAnnotationsList.size() > 0) {
                for (Annotations.SupportedAnnotation supported : supportedAnnotationsList) {
                    List<Class<?>> theList = classMap.get(supported);
                    if (theList == null) {
                        theList = new ArrayList<Class<?>>();
                        classMap.put(supported, theList);
                    }

                    theList.add(clazz);
                }
            }
        }
    }
}
