package com.hazelcast.annotation.builder;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentSkipListMap;

import com.hazelcast.common.AnnotatedClass;
import com.hazelcast.common.Annotations;
import com.hazelcast.common.ClasspathScanEventListener;
import com.hazelcast.common.ClasspathScanner;
import com.hazelcast.common.HazelcastCommonData;
import com.hazelcast.common.PriorityComparator;
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

    private static final HazelcastAnnotationBuilder INSTANCE = new HazelcastAnnotationBuilder();

    private Map<Annotations.SupportedAnnotation, List<Class<?>>> classMap = new ConcurrentSkipListMap<Annotations.SupportedAnnotation, List<Class<?>>>(new PriorityComparator());
    private IHazelcastService hazelcastService = new HazelcastService();

    public static void build(String packageName) {
        INSTANCE.buildPackage(packageName);
    }

    public static void parseObjectAnnotations(Object obj) {
        INSTANCE.buildObjectAnnotations(obj);
    }

    public static HazelcastAnnotationBuilder getInstance(){
        return INSTANCE;
    }

    public void buildPackage(String packageName){
        ClasspathScanner.scanClasspathForPackage(packageName, getClasspathScanner());
        fireEvents();
    }

    public void buildObjectAnnotations(Object obj) {
        List<AnnotatedClass> annotatedClassList = Annotations.SupportedAnnotation.getAnnotatedClassList(obj.getClass());
        fireEventsForObject(obj, annotatedClassList);
    }

    public ClasspathScannerImpl getClasspathScanner(){
        return new ClasspathScannerImpl();
    }

    public void fireEvents() {
    	for(Map.Entry<Annotations.SupportedAnnotation, List<Class<?>>> entry : classMap.entrySet()){
            
        	Annotations.SupportedAnnotation supportedAnnotation = entry.getKey();

            for (Class<?> clazz : entry.getValue()) {
                boolean eligible = HazelcastCommonData.isEligibleForParsing(clazz);

                HazelcastAnnotationProcessor processor = supportedAnnotation.getProcessor();
                if (eligible || (!eligible && processor.canBeProcessedMoreThanOnce())) {
                	processor.process(hazelcastService, clazz, clazz.getAnnotation((Class) supportedAnnotation.getClassType()));
                }

                HazelcastCommonData.classParsed(clazz);
            }
        }
    }

    public void fireEventsForObject(Object obj, List<AnnotatedClass> supportedAnnotationsList) {
        boolean eligible = HazelcastCommonData.isEligibleForParsing(obj.getClass());

        for (AnnotatedClass annotatedClass : supportedAnnotationsList) {
            HazelcastAnnotationProcessor processor = annotatedClass.getSupportedAnnotation().getProcessor();

            if (eligible || (!eligible && processor.canBeProcessedMoreThanOnce())) {
                processor.process(hazelcastService, obj, annotatedClass.getAnnotation());
            }
        }

        HazelcastCommonData.classParsed(obj.getClass());
    }

    public class ClasspathScannerImpl implements ClasspathScanEventListener {

        @Override
        public void classFound(Class<?> clazz) {
            List<AnnotatedClass> supportedAnnotatedClassList = Annotations.SupportedAnnotation.getAnnotatedClassList(clazz);

            for (AnnotatedClass supportedAnnotatedClass : supportedAnnotatedClassList) {
                List<Class<?>> theList = getOrCreateClassList(supportedAnnotatedClass);

                theList.add(clazz);
            }
        }

        private List<Class<?>> getOrCreateClassList(AnnotatedClass supportedAnnotatedClass) {
            List<Class<?>> theList = classMap.get(supportedAnnotatedClass.getSupportedAnnotation());
            if (theList == null) {
                theList = new ArrayList<Class<?>>();
                classMap.put(supportedAnnotatedClass.getSupportedAnnotation(), theList);
            }
            return theList;
        }
    }
}
