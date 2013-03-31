package com.hazelcast.annotation.spring.example.listener;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hazelcast.annotation.configuration.Configuration;
import com.hazelcast.annotation.configuration.Multicast;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IList;

@Configuration(value="MyHazelcastInstance", port = 8888, autoIncrement = true, multicast = @Multicast)
public class HZSpringListenerExample {

	public static void main(String[] args){
        new ClassPathXmlApplicationContext("spring/example/listener/listenerExample.xml");
        
        HazelcastInstance instance = Hazelcast.getHazelcastInstanceByName("MyHazelcastInstance");
        IList<String> testList = instance.getList("testList");
        testList.add("Test_Item_1");
        testList.add("Test_Item_2");
        testList.add("Test_Item_3");
    }

}
