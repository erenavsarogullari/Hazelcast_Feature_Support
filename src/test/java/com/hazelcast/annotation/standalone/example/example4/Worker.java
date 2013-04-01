package com.hazelcast.annotation.standalone.example.example4;

import com.hazelcast.annotation.HazelcastAware;
import com.hazelcast.annotation.data.*;
import org.apache.log4j.Logger;

/**
 * Date: 01/04/2013 01:54
 * Author Yusuf Soysal
 */
@HazelcastAware
public class Worker {

    private static final Logger logger = Logger.getLogger(Worker.class);

    @IQueue(maxSizePerJvm = 0, name = "testQueue")
    private com.hazelcast.core.IQueue testQueue;

    @IQueue(name = "testQueue2")
    private com.hazelcast.core.IQueue testQueue2;

    @ISet(instanceName = "MyHazelcastInstance", name = "testSet")
    private com.hazelcast.core.ISet testSet;

    @IList(name = "testList")
    private com.hazelcast.core.IList testList;

    @Distributed(name = "testMap")
    private java.util.Map testMap; // look at me! I'm a Map!

    @IMap(name="testMap2")
    private com.hazelcast.core.IMap testMap2;

    /*
     * We also have MultiMap, ITopic support
     */

    public void work(){
        logger.info("Adding data to injected list");
        testList.add("TestList 1 ");
        testList.add("TestList 2 ");
        testList.add("TestList 3 ");
        logger.info("No errors so far. Adding data to injected map");

        testMap.put(1, "testMap1-1");
        testMap.put(2, "testMap1-2");

        testMap2.put(1, "testMap2-1");
        testMap2.put(2, "testMap2-2");

        logger.info("No errors so far. Adding data to injected queue");
        testQueue.add("Test Queue");
        testQueue2.add("Test Queue2");

        logger.info("No errors so far. Adding data to injected set");
        testSet.add("Test Set");
    }
}
