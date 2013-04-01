package com.hazelcast.annotation.standalone.example.example5;

import com.hazelcast.annotation.builder.HZAware;
import com.hazelcast.annotation.builder.HazelcastAnnotationBuilder;
import com.hazelcast.annotation.configuration.Configuration;
import com.hazelcast.annotation.configuration.Multicast;
import com.hazelcast.core.Hazelcast;

/**
 * Date: 01/04/2013 01:53
 * Author Yusuf Soysal
 */
@Configuration(value="MyHazelcastInstance", port = 8888, autoIncrement = true, multicast = @Multicast)
public class Example5 {

    public static void main(String[] args) {
        HazelcastAnnotationBuilder.build("com.hazelcast.annotation.standalone.example.example5");

        Worker worker = HZAware.initialize(Worker.class);
        worker.work();

        Hazelcast.shutdownAll();

    }
}
