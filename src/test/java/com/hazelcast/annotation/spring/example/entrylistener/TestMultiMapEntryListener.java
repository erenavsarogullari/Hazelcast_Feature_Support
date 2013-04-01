package com.hazelcast.annotation.spring.example.entrylistener;

import org.apache.log4j.Logger;

import com.hazelcast.annotation.listener.EntryAdded;
import com.hazelcast.annotation.listener.EntryListener;
import com.hazelcast.annotation.listener.EntryRemoved;
import com.hazelcast.common.EntryListenerTypeEnum;
import com.hazelcast.core.EntryEvent;

@EntryListener(name ={"testMultiMap"}, type=EntryListenerTypeEnum.MULTI_MAP, needsValue=true)
public class TestMultiMapEntryListener {

	private static final Logger logger = Logger.getLogger(TestMultiMapEntryListener.class);
	
	@EntryAdded
	public void itemAdded(EntryEvent event) {
		logger.debug("Entry is added. " + event.toString());
	}
	
	@EntryRemoved
	public void itemRemoved(EntryEvent event) {
		System.out.println("Entry is removed. " + event.toString());
	}
		
}
