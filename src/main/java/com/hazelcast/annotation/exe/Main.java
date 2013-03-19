package com.hazelcast.annotation.exe;

import com.hazelcast.annotation.scanner.HazelcastAnnotationBuilder;
import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IList;

public class Main {
	public static void main(String[] args) {
		
		Config cfg = new Config();
        HazelcastInstance instance = Hazelcast.newHazelcastInstance(cfg);
        
        HazelcastAnnotationBuilder.build("com.hazelcast.annotation");
        
        IList<Object> list = instance.getList("test");
        list.add("Deneme");
        list.add("Deneme 2");
        list.add("Deneme 3");
        
        Hazelcast.shutdownAll();
	}
}
