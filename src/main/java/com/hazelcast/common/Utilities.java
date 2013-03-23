package com.hazelcast.common;

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

}
