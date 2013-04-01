package com.hazelcast.annotation.spring.example.executorservice;

import java.io.Serializable;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.springframework.context.support.ClassPathXmlApplicationContext;

import com.hazelcast.annotation.HazelcastAware;
import com.hazelcast.annotation.IExecutorService;
import com.hazelcast.annotation.configuration.Configuration;
import com.hazelcast.annotation.configuration.Multicast;
import com.hazelcast.core.Hazelcast;

@Configuration(value="MyHazelcastInstance", port = 8888, autoIncrement = true, multicast = @Multicast)
@HazelcastAware
public class HZSpringExecutorServiceExample {
	
	@IExecutorService(instanceName = "MyHazelcastInstance", corePoolSize = 2, keepAliveSeconds = 60, maxPoolSize = 5, name = "test-exec-srv")
	private java.util.concurrent.ExecutorService executorService;
	
	public static void main(String[] args){
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("spring/example/executorservice/executorServiceExample.xml");
        
        HZSpringExecutorServiceExample bean = context.getBean(HZSpringExecutorServiceExample.class);
        
        Future<String> task = bean.executorService.submit(new Echo("ExecutorService test"));
        try {
            String echoResult = task.get();
            System.out.println("RESULT : " + echoResult + " with " + bean.executorService);
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }
    }
	
	static class Echo implements Callable<String>, Serializable {
        String input = null;

        public Echo() {
        }

        public Echo(String input) {
            this.input = input;
        }

        public String call() {
            return Hazelcast.getCluster().getLocalMember().toString() + ":" + input;
        }
    }

}
