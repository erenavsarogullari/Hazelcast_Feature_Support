package com.hazelcast.annotation.builder;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Set;

import com.hazelcast.annotation.HazelcastAware;
import com.hazelcast.common.HazelcastExtraException;
import com.hazelcast.util.ConcurrentHashSet;

/**
 * Date: 24/03/2013 13:22
 * Author Yusuf Soysal
 */
public class HZAware {

    public static <T> T initialize(Class<T> clz, Object ... args){
        T instance = null;
        try {

            HazelcastAware hzAwareAnnotation = clz.getAnnotation(HazelcastAware.class);

            if( hzAwareAnnotation == null ){
                throw new HazelcastExtraException("Class is not marked as @HazelcastAware");
            }

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
