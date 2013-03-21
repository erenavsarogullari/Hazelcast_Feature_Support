package com.hazelcast.annotation.exe;

import com.hazelcast.annotation.ItemAdded;
import com.hazelcast.annotation.ItemListener;
import com.hazelcast.common.ItemTypeEnum;
import com.hazelcast.core.ItemEvent;

@ItemListener(distributedObjectName ={ "testList1", "testList2", "testSet1"}, 
								type = {ItemTypeEnum.LIST, ItemTypeEnum.SET}, 
								needsValue=true)
public class TestItemListener {	
	
	@ItemAdded
	public void myListIsGrowing(ItemEvent event) {
		System.out.println("Item is really added " + event.toString());
	}
}
