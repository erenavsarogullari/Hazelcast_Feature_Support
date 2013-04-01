package com.hazelcast.annotation.spring.example.instance;

import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hazelcast.annotation.HazelcastAware;
import com.hazelcast.annotation.configuration.Configuration;
import com.hazelcast.annotation.configuration.Multicast;
import com.hazelcast.annotation.data.HZInstance;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;

@Configuration(value="MyHazelcastInstance", port = 8888, autoIncrement = true, multicast = @Multicast)
@HazelcastAware
public class HZSpringInstanceExample {

	private static final Logger logger = Logger.getLogger(HZSpringInstanceExample.class);
	
	@HZInstance("MyHazelcastInstance")
    private HazelcastInstance instance;
    
    public static void main(String[] args){
    	ApplicationContext context = new ClassPathXmlApplicationContext("spring/example/instance/instanceExample.xml");
    	HZSpringInstanceExample hzSpringInstanceExample = context.getBean(HZSpringInstanceExample.class);
    	IMap testMap = hzSpringInstanceExample.instance.getMap("testMap");
    	testMap.put("1", "Test Entry");
    	
    	logger.debug("testMap => " + testMap);
    }

}
