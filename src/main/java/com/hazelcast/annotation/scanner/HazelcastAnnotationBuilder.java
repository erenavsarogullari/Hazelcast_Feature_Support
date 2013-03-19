/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.hazelcast.annotation.scanner;

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

import com.hazelcast.annotation.ExecutorService;
import com.hazelcast.annotation.ItemListener;
import com.hazelcast.annotation.processor.ExecutorServiceProcessor;
import com.hazelcast.annotation.processor.ItemListenerProcessor;
import com.hazelcast.common.Annotations;
import com.hazelcast.common.Annotations.SupportedAnnotation;
import com.hazelcast.common.SystemConstants;

/**
 *
 * @author yusufsoysal
 */
public class HazelcastAnnotationBuilder {

    private static ClassLoader currentCL;
    private static Map<Class<?>, List<HazelcastAnnotationProcessor>> processorMap = new ConcurrentHashMap<Class<?>, List<HazelcastAnnotationProcessor>>();
    private static Map<SupportedAnnotation, List<Class<?>>> classMap = new ConcurrentHashMap<SupportedAnnotation, List<Class<?>>>();
    
    static {
        init();
    }

    private static void init() {
        loadProcessorMap();        
        registerAnnotationsToProcessors();
    }

	private static void loadProcessorMap() {
		for (SupportedAnnotation supportedAnnotations : Annotations.SupportedAnnotation.values()) {
            processorMap.put(supportedAnnotations.getClassType(), new ArrayList<HazelcastAnnotationProcessor>());
        }
	}
	
	private static void registerAnnotationsToProcessors() {
		registerAnnotationToProcessor(ItemListener.class, new ItemListenerProcessor());
		registerAnnotationToProcessor(ExecutorService.class, new ExecutorServiceProcessor());
	}

    private static void registerAnnotationToProcessor(Class<?> clazz, HazelcastAnnotationProcessor processor) {
        processorMap.get(clazz).add(processor);
    }
    
    private static void fireEvents() {
        for (SupportedAnnotation supportedAnnotation : Annotations.SupportedAnnotation.values()) {
            List<Class<?>> clazzListOfSupportedAnnotation = classMap.get(supportedAnnotation);

            if (clazzListOfSupportedAnnotation != null) {
                for (Class<?> clazz : clazzListOfSupportedAnnotation) {
                    List<HazelcastAnnotationProcessor> processorListOfSupportedAnnotation = processorMap.get(supportedAnnotation.getClassType());
                    for (HazelcastAnnotationProcessor processor : processorListOfSupportedAnnotation) {
                        processor.process(clazz, clazz.getAnnotation((Class)supportedAnnotation.getClassType()));
                    }
                }
            }
        }
    }
    
    public static void build(String packageName) {
    	scanClasspathForPackage(packageName);
    	fireEvents();
    }

    private static List<Class<?>> scanClasspathForPackage(String packageName) {
        List<Class<?>> clzList = new ArrayList<Class<?>>();
        String folderName = packageName.replace('.', File.separatorChar);
        currentCL = HazelcastAnnotationBuilder.class.getClassLoader();

        try {
            Enumeration<URL> resources = currentCL.getResources(folderName);

            if (resources != null) {
                while (resources.hasMoreElements()) {
                    URL pckResource = resources.nextElement();

                    File directory = null;
                    
                    try {
                        directory = new File(pckResource.toURI());
                    } catch (Exception e) {
                        directory = null;
                    }

                    if (directory == null) {
                        clzList.addAll(searchJarFiles(pckResource.getFile(), folderName));
                    } else {
                        // search folders
                        clzList.addAll(searchFolder(directory, packageName));
                    }
                }

            }

        } catch (IOException e1) {
            e1.printStackTrace();
        }

        return clzList;
    }

    private static List<Class<?>> searchJarFiles(String path, String packagePath) {
        List<Class<?>> clsList = new ArrayList<Class<?>>();

        String jarPath = path.replaceFirst("[.]jar[!].*", ".jar").replaceFirst("file:", "");

        JarFile jarFile;
        try {
            jarFile = new JarFile(jarPath);

            Enumeration<JarEntry> entries = jarFile.entries();
            while (entries.hasMoreElements()) {
                JarEntry entry = entries.nextElement();
                String entryName = entry.getName();
                if (entryName.startsWith(packagePath)) {
                    if (entryName.endsWith(SystemConstants.CLASS_SUFFIX)) {
                        String clsName = entryName.replace(File.separatorChar, '.').substring(0, entryName.length() - 6); // strip the class suffix
                        try {
                            Class<?> clazz = Class.forName(clsName, false, currentCL);
                            checkAndAddForSupported(clazz);
                        } catch (Throwable e) {
                            // so cannot load this class. should I throw ex?
                            e.printStackTrace();
                        }
                    }
                }
            }
        } catch (IOException e) {
            // should I throw ex?
            e.printStackTrace();
        }

        return clsList;

    }

    private static List<Class<?>> searchFolder(File directory, String pckName) {
        List<Class<?>> clsList = new ArrayList<Class<?>>();
        if (directory.isDirectory() && directory.exists()) {
            String[] fileList = directory.list();

            for (String file : fileList) {
                if (file.endsWith(SystemConstants.CLASS_SUFFIX)) {
                    String clsName = pckName + "." + file.substring(0, file.length() - 6); // strip the class suffix

                    // test whether it is really a class
                    try {
                        Class<?> clazz = Class.forName(clsName, false, currentCL);
                        checkAndAddForSupported(clazz);
                    } catch (Throwable e) {
                        // so cannot load this class. shpuld I throw ex?
                        e.printStackTrace();
                    }
                } else {
                    // this may be another packege
                    String newPckName = pckName + "." + file;
                    String newDirectory = directory.getAbsolutePath() + File.separator + file;
                    clsList.addAll(searchFolder(new File(newDirectory), newPckName));
                }
            }
        }

        return clsList;
    }

    private static void checkAndAddForSupported(Class<?> clazz) {
        List<SupportedAnnotation> supportedAnnotationsList = SupportedAnnotation.getSupportedAnnotations(clazz);
        if (supportedAnnotationsList.size() > 0) {
            for (SupportedAnnotation supported : supportedAnnotationsList) {
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
