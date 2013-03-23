package com.hazelcast.common;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.Enumeration;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

/**
 * Date: 23/03/2013 16:18
 * Author Yusuf Soysal
 */
public class ClasspathScanner {
    private static ClassLoader currentCL;

    public static void scanClasspathForPackage(String packageName, ClasspathScanEventListener listener) {
        String folderName = packageName.replace('.', File.separatorChar);
        currentCL = ClasspathScanner.class.getClassLoader();

        try {
            Enumeration<URL> resources = currentCL.getResources(folderName);

            if (resources != null) {
                while (resources.hasMoreElements()) {
                    URL pckResource = resources.nextElement();

                    File directory;

                    try {
                        directory = new File(pckResource.toURI());
                    } catch (Exception e) {
                        directory = null;
                    }

                    if (directory == null) {
                        searchJarFiles(listener, pckResource.getFile(), folderName);
                    } else {
                        // search folders
                        searchFolder(listener, directory, packageName);
                    }
                }

            }

        } catch (IOException e1) {
            e1.printStackTrace();
        }
    }

    private static void searchJarFiles(ClasspathScanEventListener listener, String path, String packagePath) {
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
                            listener.classFound(clazz);
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

    }

    private static void searchFolder(ClasspathScanEventListener listener, File directory, String pckName) {
        if (directory.isDirectory() && directory.exists()) {
            String[] fileList = directory.list();

            for (String file : fileList) {
                if (file.endsWith(SystemConstants.CLASS_SUFFIX)) {
                    String clsName = pckName + "." + file.substring(0, file.length() - 6); // strip the class suffix

                    // test whether it is really a class
                    try {
                        Class<?> clazz = Class.forName(clsName, false, currentCL);
                        listener.classFound(clazz);
                    } catch (Throwable e) {
                        // so cannot load this class. shpuld I throw ex?
                        e.printStackTrace();
                    }
                } else {
                    // this may be another packege
                    String newPckName = pckName + "." + file;
                    String newDirectory = directory.getAbsolutePath() + File.separator + file;
                    searchFolder(listener, new File(newDirectory), newPckName);
                }
            }
        }
    }

}
