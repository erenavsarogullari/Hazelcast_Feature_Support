package com.hazelcast.annotation.spring.example.entrylistener;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hazelcast.annotation.HazelcastAware;
import com.hazelcast.annotation.configuration.Configuration;
import com.hazelcast.annotation.configuration.Multicast;
import com.hazelcast.annotation.data.IMap;
import com.hazelcast.annotation.data.MultiMap;

@Configuration(value="MyHazelcastInstance", port = 8888, autoIncrement = true, multicast = @Multicast)
@HazelcastAware
public class HZSpringEntryListenerExample {
	
	@IMap(name = "testMap")
	com.hazelcast.core.IMap testMap;
	
	@MultiMap(name = "testMultiMap")
	com.hazelcast.core.MultiMap testMultiMap;
	
	public static void main(String[] args){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/example/entrylistener/entryListenerExample.xml");
        
        HZSpringEntryListenerExample bean = context.getBean(HZSpringEntryListenerExample.class);
        bean.testMap.put("1", "Test_Map_Entry_1");
        bean.testMap.put("2", "Test_Map_Entry_2");
        bean.testMap.put("3", "Test_Map_Entry_3");
        
        bean.testMultiMap.put("1", "Test_MultiMap_Entry_1");
        bean.testMultiMap.put("2", "Test_MultiMap_Entry_2");
        bean.testMultiMap.put("3", "Test_MultiMap_Entry_3");
        
    }

}
