package com.hazelcast.annotation.exe;

import com.hazelcast.annotation.listener.EntryAdded;
import com.hazelcast.annotation.listener.EntryListener;
import com.hazelcast.annotation.listener.EntryRemoved;
import com.hazelcast.common.EntryListenerTypeEnum;
import com.hazelcast.core.EntryEvent;

/**
 * Test Entry Listener Class
 *
 * @author Eren Avsarogullari
 * @author Yusuf Soysal
 * @since 17 March 2013
 * @version 1.0.0
 *
 */
@EntryListener(name = {"testMultiMap", "testMultiMap2"}, type=EntryListenerTypeEnum.MULTI_MAP, needsValue=true)
public class TestMultiMapEntryListener {	
	
	@EntryAdded
	public void entryAdded(EntryEvent event) {
		System.out.println("MULTI_MAP - Entry is added " + event.toString());
	}
	
	@EntryRemoved
	public void entryRemoved(EntryEvent event) {
		System.out.println("MULTI_MAP - Entry is remowed " + event.toString());
	}
}
