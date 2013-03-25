package com.hazelcast.annotation.exe;

import java.io.Serializable;
import java.lang.reflect.Field;
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
import com.hazelcast.annotation.data.IMap;
import com.hazelcast.annotation.data.IQueue;
import com.hazelcast.annotation.data.ISet;
import com.hazelcast.annotation.data.MultiMap;
import com.hazelcast.config.MultiMapConfig.ValueCollectionType;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;

@Configuration(value="MyHazelcastInstance", port = 8888, autoIncrement = true, multicast = @Multicast)
@HazelcastAware
public class Main {
	
	@IExecutorService(corePoolSize = 2, keepAliveSeconds = 60, maxPoolSize = 5, name = "test-exec-srv")
	private java.util.concurrent.ExecutorService executorService;
	
	@IQueue(maxSizePerJvm = 0, name = "testQueue")
    private com.hazelcast.core.IQueue testQueue;
	
	@IQueue(maxSizePerJvm = 0, name = "testQueue2")
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
    
    @IMap(name="testMap")
	private com.hazelcast.core.IMap testMap;
    
    @IMap(name="testMap2")
	private com.hazelcast.core.IMap testMap2;
	
	@MultiMap(name = "testMultiMap", valueCollectionType = ValueCollectionType.SET)
	private com.hazelcast.core.MultiMap testMultiMap;
	
	@MultiMap(name = "testMultiMap2", valueCollectionType = ValueCollectionType.SET)
	private com.hazelcast.core.MultiMap testMultiMap2;	
	
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
                
        main.testMap.put(1, "testMap1-1");
        main.testMap.put(2, "testMap1-2");
        
        main.testMap2.put(1, "testMap2-1");
        main.testMap2.put(2, "testMap2-2");

	    main.testMultiMap.put(1, "TestMultiMap1");
		main.testMultiMap.put(1, "TestMultiMap2");
		main.testMultiMap.put(2, "TestMultiMap3");
		
		main.testMultiMap2.put(3, "TestMultiMap3");
		main.testMultiMap2.put(4, "TestMultiMap4");
		main.testMultiMap2.put(5, "TestMultiMap5");

        main.testQueue.add("Test Queue");
        main.testQueue2.add("Test Queue2");

        main.testSet.add("Test Set");
        main.testSet2.add("Test Set2");
     
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
