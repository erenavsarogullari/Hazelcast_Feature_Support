package com.hazelcast.annotation.spring.example.messagelistener;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hazelcast.annotation.HazelcastAware;
import com.hazelcast.annotation.configuration.Configuration;
import com.hazelcast.annotation.configuration.Multicast;
import com.hazelcast.annotation.data.ITopic;

@Configuration(value="MyHazelcastInstance", port = 8888, autoIncrement = true, multicast = @Multicast)
@HazelcastAware
public class HZSpringMessageListenerExample {
	
	@ITopic(name = "testTopic")
	com.hazelcast.core.ITopic testTopic;
	
	@ITopic(name = "testTopic2")
	com.hazelcast.core.ITopic testTopic2;
	
	public static void main(String[] args){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/example/messagelistener/messageListenerExample.xml");
        
        HZSpringMessageListenerExample bean = context.getBean(HZSpringMessageListenerExample.class);
        bean.testTopic.publish("Test_Topic_Message_1");
        bean.testTopic.publish("Test_Topic_Message_2");
        bean.testTopic.publish("Test_Topic_Message_3");

        bean.testTopic2.publish("Test_Topic_2_Message_1");
        bean.testTopic2.publish("Test_Topic_2_Message_2");
        bean.testTopic2.publish("Test_Topic_2_Message_3");
        
    }

}
