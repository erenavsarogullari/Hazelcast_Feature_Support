package com.hazelcast.annotation.standalone.example.example2;

import com.hazelcast.annotation.HazelcastAware;
import com.hazelcast.annotation.data.HZInstance;
import com.hazelcast.core.HazelcastInstance;

/**
 * Date: 01/04/2013 01:54
 * Author Yusuf Soysal
 */
@HazelcastAware
public class Worker {
    @HZInstance
    private HazelcastInstance instance;

    public void work(){
        if( instance == null ){
            System.out.println("instance is null! Example doesn't work as expected");
        } else {
            System.out.println("Worked, perfect!");
        }
    }
}
