package com.hazelcast.annotation.exe;

import com.hazelcast.annotation.HazelcastAware;
import com.hazelcast.annotation.listener.ItemAdded;
import com.hazelcast.annotation.listener.ItemListener;
import com.hazelcast.core.ItemEvent;

/**
 * Test Item Listener Class
 *
 * @author Eren Avsarogullari
 * @author Yusuf Soysal
 * @since 17 March 2013
 * @version 1.0.0
 *
 */
@HazelcastAware
@ItemListener(distributedObjectName ={"testList", "testList2", "testSet", 
		                                "testSet2", "testQueue", "testQueue2"},	needsValue=true)
public class TestItemListener {	
	
	@ItemAdded
	public void myListIsGrowing(ItemEvent event) {
		System.out.println("Item is really added " + event.toString());
	}
}
