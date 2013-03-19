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

public class Main {
	
	@com.hazelcast.annotation.ExecutorService(corePoolSize = 2, keepAliveSeconds = 60, maxPoolSize = 5, name = "test-exec-srv")
	static
	ExecutorService executorService;
	
	public static void main(String[] args) {
		
		Config cfg = new Config();
        HazelcastInstance instance = Hazelcast.newHazelcastInstance(cfg);
        
        HazelcastAnnotationBuilder.build("com.hazelcast.annotation");
        
        IList<Object> list = instance.getList("test");
        list.add("Deneme");
        list.add("Deneme 2");
        list.add("Deneme 3");
        
        Future<String> task = executorService.submit(new Echo("Eren"));
		   try {
			String echoResult = task.get();
			System.out.println("RESULT : " + echoResult + " with " + executorService);
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
