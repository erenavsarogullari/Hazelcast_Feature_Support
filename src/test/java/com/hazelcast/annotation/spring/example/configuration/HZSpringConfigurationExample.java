package com.hazelcast.annotation.spring.example.configuration;

import com.hazelcast.annotation.configuration.Configuration;
import com.hazelcast.annotation.configuration.Multicast;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Date: 31/03/2013 13:28
 * Author Yusuf Soysal
 */
@Configuration(value="MyHazelcastInstance", port = 8888, autoIncrement = true, multicast = @Multicast)
public class HZSpringConfigurationExample {

    public static void main(String[] args){
        ClassPathXmlApplicationContext appContext = new ClassPathXmlApplicationContext("spring/example/configuration/configurationExample.xml");

    }

}
