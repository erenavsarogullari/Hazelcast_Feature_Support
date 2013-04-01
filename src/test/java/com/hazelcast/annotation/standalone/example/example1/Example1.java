package com.hazelcast.annotation.standalone.example.example1;

import com.hazelcast.annotation.builder.HazelcastAnnotationBuilder;
import com.hazelcast.annotation.configuration.Configuration;
import com.hazelcast.annotation.configuration.Multicast;
import com.hazelcast.core.Hazelcast;

@Configuration(value="MyHazelcastInstance", port = 8888, autoIncrement = true, multicast = @Multicast)
public class Example1{

	public static void main(String[] args) {
        HazelcastAnnotationBuilder.build("com.hazelcast.annotation.standalone.example.example1");
        
        Hazelcast.shutdownAll();       
        
	}
}
