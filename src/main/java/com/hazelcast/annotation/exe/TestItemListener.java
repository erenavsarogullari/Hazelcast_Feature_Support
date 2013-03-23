package com.hazelcast.annotation.exe;

import com.hazelcast.annotation.ItemAdded;
import com.hazelcast.annotation.ItemListener;
import com.hazelcast.common.ItemTypeEnum;
import com.hazelcast.core.ItemEvent;

@ItemListener(distributedObjectName ={"testList1", "testList2", "testSet1", "testQueue"}, 
								type = {ItemTypeEnum.LIST, ItemTypeEnum.SET, ItemTypeEnum.QUEUE}, 
								needsValue=true)
public class TestItemListener {	
	
	@ItemAdded
	public void myListIsGrowing(ItemEvent event) {
		System.out.println("Item is really added " + event.toString());
	}
}
