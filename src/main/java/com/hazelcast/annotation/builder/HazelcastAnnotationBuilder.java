package com.hazelcast.annotation.builder;

import com.hazelcast.common.AnnotatedClass;
import com.hazelcast.common.Annotations;
import com.hazelcast.common.ClasspathScanEventListener;
import com.hazelcast.common.ClasspathScanner;
import com.hazelcast.srv.HazelcastService;
import com.hazelcast.srv.IHazelcastService;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Hazelcast Annotation Builder
 *
 * @author Yusuf Soysal
 * @author Eren Avsarogullari
 * @version 1.0.0
 * @since 17 March 2013
 */
public class HazelcastAnnotationBuilder {

    private static Map<Annotations.SupportedAnnotation, List<Class<?>>> classMap = new ConcurrentHashMap<Annotations.SupportedAnnotation, List<Class<?>>>();
    private static IHazelcastService hazelcastService = new HazelcastService();

    public static void build(String packageName) {
        ClasspathScanner.scanClasspathForPackage(packageName, new ClasspathScannerImpl());
        fireEvents();
    }

    public static void parseObjectAnnotations(Object obj) {
        List<AnnotatedClass> supportedAnnotationsList = Annotations.SupportedAnnotation.getSupportedAnnotations(obj.getClass());
        if (supportedAnnotationsList.size() > 0) {

            fireEventsForObject(obj, supportedAnnotationsList);
        }
    }

    private static void fireEvents() {

        for (Annotations.SupportedAnnotation supportedAnnotation : Annotations.SupportedAnnotation.values()) {
            List<Class<?>> clazzListOfSupportedAnnotation = classMap.get(supportedAnnotation);

            if (clazzListOfSupportedAnnotation != null) {
                for (Class<?> clazz : clazzListOfSupportedAnnotation) {
                    boolean eligible = HZAware.eligibleForParsing(clazz);

                    HazelcastAnnotationProcessor processor = supportedAnnotation.getProcessor();
                    if (eligible || (!eligible && processor.canBeProcessedMoreThanOnce())) {
                        processor.process(hazelcastService, clazz, clazz.getAnnotation((Class) supportedAnnotation.getClassType()));
                    }

                    HZAware.classParsed(clazz);
                }
            }
        }
    }

    private static void fireEventsForObject(Object obj, List<AnnotatedClass> supportedAnnotationsList) {
        boolean eligible = HZAware.eligibleForParsing(obj.getClass());

        for (AnnotatedClass annotatedClass : supportedAnnotationsList) {
            HazelcastAnnotationProcessor processor = annotatedClass.getSupportedAnnotation().getProcessor();

            if (eligible || (!eligible && processor.canBeProcessedMoreThanOnce())) {
                processor.process(hazelcastService, obj, annotatedClass.getAnnotation());
            }
        }

        HZAware.classParsed(obj.getClass());
    }

    public static class ClasspathScannerImpl implements ClasspathScanEventListener {

        @Override
        public void classFound(Class<?> clazz) {
            List<AnnotatedClass> supportedAnnotationsList = Annotations.SupportedAnnotation.getSupportedAnnotations(clazz);
            if (supportedAnnotationsList.size() > 0) {
                for (AnnotatedClass annotatedClass : supportedAnnotationsList) {
                    List<Class<?>> theList = classMap.get(annotatedClass.getSupportedAnnotation());
                    if (theList == null) {
                        theList = new ArrayList<Class<?>>();
                        classMap.put(annotatedClass.getSupportedAnnotation(), theList);
                    }

                    theList.add(clazz);
                }
            }
        }
    }
}
