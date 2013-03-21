package com.hazelcast.annotation.exe;

import com.hazelcast.annotation.EntryAdded;
import com.hazelcast.annotation.EntryListener;
import com.hazelcast.annotation.EntryRemoved;
import com.hazelcast.common.EntryTypeEnum;
import com.hazelcast.core.EntryEvent;

@EntryListener(distributedObjectName = {"testMap1", "testMultiMap1"}, 
								type = {EntryTypeEnum.MAP, EntryTypeEnum.MULTI_MAP}, 
								needsValue=true)

public class TestEntryListener {	
	
	@EntryAdded
	public void entryAdded(EntryEvent event) {
		System.out.println("Entry is added " + event.toString());
	}
	
	@EntryRemoved
	public void entryRemoved(EntryEvent event) {
		System.out.println("Entry is remowed " + event.toString());
	}
}
