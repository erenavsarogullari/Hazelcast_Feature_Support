package com.hazelcast.annotation.exe;

import com.hazelcast.annotation.ItemAdded;
import com.hazelcast.annotation.ItemListener;
import com.hazelcast.common.ItemTypeEnum;
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
@ItemListener(distributedObjectName ={"testList", "testList2", "testSet", "testSet2", "testQueue", "testQueue2"}, 
								type = {ItemTypeEnum.LIST, ItemTypeEnum.SET, ItemTypeEnum.QUEUE}, 
								needsValue=true)
public class TestItemListener {	
	
	@ItemAdded
	public void myListIsGrowing(ItemEvent event) {
		System.out.println("Item is really added " + event.toString());
	}
}
