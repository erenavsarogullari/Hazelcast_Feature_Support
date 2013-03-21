package com.hazelcast.annotation.exe;

import java.io.Serializable;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

import com.hazelcast.annotation.scanner.HazelcastAnnotationBuilder;
import com.hazelcast.config.Config;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IList;
import com.hazelcast.core.IMap;
import com.hazelcast.core.ISet;
import com.hazelcast.core.MultiMap;

public class Main {
	
	@com.hazelcast.annotation.ExecutorService(corePoolSize = 2, keepAliveSeconds = 60, maxPoolSize = 5, name = "test-exec-srv")
	static
	ExecutorService executorService;
	
	public static void main(String[] args) {
		
		Config cfg = new Config();
        HazelcastInstance instance = Hazelcast.newHazelcastInstance(cfg);
        
        HazelcastAnnotationBuilder.build("com.hazelcast.annotation");
        
        IList<String> testList1 = instance.getList("testList1");
        testList1.add("Deneme 1 " + testList1);
        testList1.add("Deneme 2 ");
        testList1.add("Deneme 3 ");
        
        IList<String> testList2 = instance.getList("testList2");
        testList2.add("Deneme 4 " + testList2);
        testList2.add("Deneme 5 ");
        testList2.add("Deneme 6 ");
        
        ISet<String> testSet1 = instance.getSet("testSet1");
        testSet1.add("Deneme 7 " + testSet1);
        testSet1.add("Deneme 8 ");
        testSet1.add("Deneme 9 ");
        
        Future<String> task = executorService.submit(new Echo("Eren"));
		   try {
			String echoResult = task.get();
			System.out.println("RESULT : " + echoResult + " with " + executorService);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} catch (ExecutionException e) {
			e.printStackTrace();
		}
		   
		IMap<Integer, String> testMap = instance.getMap("testMap1");
		testMap.put(1, "testMap1");
		testMap.put(2, "testMap2");
		
		MultiMap<Integer, String> testMultiMap = instance.getMultiMap("testMultiMap1");
		testMultiMap.put(1, "TestMultiMap1");
		testMultiMap.put(1, "TestMultiMap2");
		testMultiMap.put(2, "TestMultiMap3");
				
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
