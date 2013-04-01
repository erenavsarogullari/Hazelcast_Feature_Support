package com.hazelcast.annotation.spring.example.itemlistener;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hazelcast.annotation.HazelcastAware;
import com.hazelcast.annotation.configuration.Configuration;
import com.hazelcast.annotation.configuration.Multicast;
import com.hazelcast.annotation.data.IList;

@Configuration(value="MyHazelcastInstance", port = 8888, autoIncrement = true, multicast = @Multicast)
@HazelcastAware
public class HZSpringListenerExample {
	
	@IList(name = "testList")
	com.hazelcast.core.IList testList;
	
	public static void main(String[] args){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/example/itemlistener/itemListenerExample.xml");
        
        HZSpringListenerExample bean = context.getBean(HZSpringListenerExample.class);
        bean.testList.add("Test_Item_1");
        bean.testList.add("Test_Item_2");
        bean.testList.add("Test_Item_3");
    }

}
