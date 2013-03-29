package com.hazelcast.common;

import com.hazelcast.util.ConcurrentHashSet;

import java.util.Set;

/**
 * Date: 27/03/2013 21:17
 * Author Yusuf Soysal
 */
public class HazelcastCommonData {

    private static Set<Class<?>> parsedClasses = new ConcurrentHashSet<Class<?>>();

    private static String hzInstanceName = null;

    public static boolean isEligibleForParsing(Class<?> clz){
        return !parsedClasses.contains(clz);
    }

    public static boolean classParsed(Class<?> clz){
        return parsedClasses.add(clz);
    }

    public static String getHzInstanceName() {
        return hzInstanceName;
    }

    public static void setHzInstanceName(String hzInstanceName) {
        HazelcastCommonData.hzInstanceName = hzInstanceName;
    }
}
