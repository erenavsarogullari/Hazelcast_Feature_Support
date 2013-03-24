package com.hazelcast.annotation.exe;

import java.io.Serializable;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;

import java.util.concurrent.Future;

import com.hazelcast.annotation.*;
import com.hazelcast.annotation.builder.HZAware;
import com.hazelcast.annotation.builder.HazelcastAnnotationBuilder;
import com.hazelcast.annotation.configuration.Configuration;
import com.hazelcast.annotation.configuration.Multicast;
import com.hazelcast.annotation.data.HZInstance;
import com.hazelcast.annotation.data.IList;
import com.hazelcast.annotation.data.IQueue;
import com.hazelcast.annotation.data.ISet;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IMap;
import com.hazelcast.core.MultiMap;

@Configuration(value="MyHazelcastInstance", port = 8888, autoIncrement = true, multicast = @Multicast)
@HazelcastAware
public class Main {
	
	@IExecutorService(corePoolSize = 2, keepAliveSeconds = 60, maxPoolSize = 5, name = "test-exec-srv")
	private java.util.concurrent.ExecutorService executorService;
	
	@IQueue(backingMapRef = 5, maxSizePerJvm = 0, name = "testQueue")
    private com.hazelcast.core.IQueue testQueue;
	
	@IQueue(backingMapRef = 5, maxSizePerJvm = 0, name = "testQueue2")
    private com.hazelcast.core.IQueue testQueue2;
	
	@ISet(name = "testSet")
    private com.hazelcast.core.ISet testSet;
	
	@ISet(name = "testSet2")
    private com.hazelcast.core.ISet testSet2;
	
	@IList(name = "testList")
    private com.hazelcast.core.IList testList;
	
	@IList(name = "testList2")
    private com.hazelcast.core.IList testList2;

    @HZInstance("MyHazelcastInstance")
    private HazelcastInstance instance;
	
	public static void main(String[] args) {
        HazelcastAnnotationBuilder.build("com.hazelcast.annotation");

        // think about this
        // HZAware.initialize(new Main());
        Main main = HZAware.initialize(Main.class);


        main.testList.add("TestList 1 ");
        main.testList.add("TestList 2 ");
        main.testList.add("TestList 3 ");

        main.testList2.add("TestList2 4 ");
        main.testList2.add("TestList2 5 ");
        main.testList2.add("TestList2 6 ");
                
        IMap<Integer, String> testMap = main.instance.getMap("testMap1");
		testMap.put(1, "testMap1");
		testMap.put(2, "testMap2");
		
		MultiMap<Integer, String> testMultiMap = main.instance.getMultiMap("testMultiMap1");
		testMultiMap.put(1, "TestMultiMap1");
		testMultiMap.put(1, "TestMultiMap2");
		testMultiMap.put(2, "TestMultiMap3");

        main.testQueue.add("Test Queue");
        main.testQueue2.add("Test Queue2");

        main.testSet.add("Test Set");
        main.testSet2.add("Test Set2");
        
        
        Future<String> task = main.executorService.submit(new Echo("Eren"));
		   try {
			String echoResult = task.get();
			System.out.println("RESULT : " + echoResult + " with " + main.executorService);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}		 
        
        Hazelcast.shutdownAll();       
        
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
