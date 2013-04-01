package com.hazelcast.common;

import com.hazelcast.annotation.HazelcastAware;
import com.hazelcast.annotation.builder.HazelcastAnnotationBuilder;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * Date: 23/03/2013 14:07
 * Author Yusuf Soysal
 */
public class Utilities {

    public static boolean isUsable(String value){
        boolean returnVal = false;

        if( value != null && !value.trim().equals("") ){
            returnVal = true;
        }

        return returnVal;
    }

    public static <T> T createAnnotatedInstance(Class<T> clz, Object ... args){
        T instance = createInstance(clz, args);
        if( clz.isAnnotationPresent(HazelcastAware.class) ){
            HazelcastAnnotationBuilder.parseObjectAnnotations(instance);
        }

        return instance;
    }

    public static <T> T createInstance(Class<T> clz, Object ... args){
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

        } catch (InstantiationException e) {
            throw new HazelcastExtraException("Cannot create " + clz.getName() + " instance", e);
        } catch (IllegalAccessException e) {
            throw new HazelcastExtraException("Cannot call " + clz.getName() + " constructor", e);
        } catch (NoSuchMethodException e) {
            throw new HazelcastExtraException("Cannot find a suitable constructor candidate for " + clz.getName() + " with given arguments", e);
        } catch (InvocationTargetException e) {
            throw new HazelcastExtraException("Cannot instantiate  " + clz.getName() + " instance", e);
        }

        return instance;
    }

}
