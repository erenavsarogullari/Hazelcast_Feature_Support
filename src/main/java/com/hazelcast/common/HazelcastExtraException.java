package com.hazelcast.common;

import com.hazelcast.core.HazelcastException;

/**
 * Date: 25/03/2013 23:30
 * Author Yusuf Soysal
 */
public class HazelcastExtraException extends HazelcastException {
    
    public HazelcastExtraException() {
    }

    public HazelcastExtraException(String message) {
        super(message);
    }

    public HazelcastExtraException(String message, Throwable cause) {
        super(message, cause);
    }

    public HazelcastExtraException(Throwable cause) {
        super(cause);
    }
}
