package com.hazelcast.annotation.standalone.example.example5;

import com.hazelcast.annotation.HazelcastAware;
import com.hazelcast.annotation.IExecutorService;
import com.hazelcast.annotation.data.*;
import com.hazelcast.core.Hazelcast;
import org.apache.log4j.Logger;

import java.io.Serializable;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

/**
 * Date: 01/04/2013 01:54
 * Author Yusuf Soysal
 */
@HazelcastAware
public class Worker {

    private static final Logger logger = Logger.getLogger(Worker.class);

    @IExecutorService(instanceName = "MyHazelcastInstance", corePoolSize = 2, keepAliveSeconds = 60, maxPoolSize = 5, name = "test-exec-srv")
    private java.util.concurrent.ExecutorService executorService;


    public void work(){
        Future<String> task = executorService.submit(new Echo("ExecutorService test"));
        try {
            String echoResult = task.get();
            logger.info("RESULT : " + echoResult + " with " + executorService);
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
