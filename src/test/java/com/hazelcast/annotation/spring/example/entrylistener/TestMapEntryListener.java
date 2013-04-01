package com.hazelcast.annotation.spring.example.entrylistener;

import org.apache.log4j.Logger;

import com.hazelcast.annotation.listener.EntryAdded;
import com.hazelcast.annotation.listener.EntryListener;
import com.hazelcast.annotation.listener.EntryRemoved;
import com.hazelcast.common.EntryListenerTypeEnum;
import com.hazelcast.core.EntryEvent;

@EntryListener(name ={"testMap", "testConcurrentHashMap"}, type=EntryListenerTypeEnum.MAP, needsValue=true)
public class TestMapEntryListener {

	private static final Logger logger = Logger.getLogger(TestMapEntryListener.class);
	
	@EntryAdded
	public void itemAdded(EntryEvent event) {
		logger.debug("Entry is added. " + event.toString());
	}
	
	@EntryRemoved
	public void itemRemoved(EntryEvent event) {
		System.out.println("Entry is removed. " + event.toString());
	}	
	
}
