package com.hazelcast.annotation.builder;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Date: 24/03/2013 13:22
 * Author Yusuf Soysal
 */
public class HZAware {

    private static List<Class<?>> parsedClasses = new ArrayList<Class<?>>();

    public static <T> T initialize(Class<T> clz, Object ... args){
        T instance = null;
        try {
            if( args.length > 0 ){
                Class<?>[] clzTypes = new Class<?>[args.length];
                int index = 0;
                for( Object obj : args ){
                    clzTypes[ index++ ] = args.getClass();
                }

                Constructor<T> constructor = clz.getConstructor(clzTypes);
                instance = constructor.newInstance(args);
            }else {
                instance = clz.newInstance();
            }

            HazelcastAnnotationBuilder.parseObjectAnnotations(instance);

        } catch (InstantiationException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (IllegalAccessException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (NoSuchMethodException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        } catch (InvocationTargetException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }

        return instance;
    }

    public static boolean eligibleForParsing(Class<?> clz){
        return !parsedClasses.contains(clz);
    }

    public static boolean classParsed(Class<?> clz){
        return parsedClasses.add(clz);
    }
}
