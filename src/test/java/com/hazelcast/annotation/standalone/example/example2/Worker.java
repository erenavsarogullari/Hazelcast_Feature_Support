package com.hazelcast.annotation.standalone.example.example2;

import com.hazelcast.annotation.HazelcastAware;
import com.hazelcast.annotation.data.HZInstance;
import com.hazelcast.core.HazelcastInstance;
import org.apache.log4j.Logger;

/**
 * Date: 01/04/2013 01:54
 * Author Yusuf Soysal
 */
@HazelcastAware
public class Worker {
    private static final Logger logger = Logger.getLogger(Worker.class);

    @HZInstance
    private HazelcastInstance instance;

    public void work(){
        if( instance == null ){
            logger.error("instance is null! Example doesn't work as expected");
        } else {
            logger.info("Worked, perfect! Library injected the instance");
        }
    }
}
