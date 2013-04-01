package com.hazelcast.annotation.exe;

import com.hazelcast.annotation.HazelcastAware;
import com.hazelcast.annotation.data.HZInstance;
import com.hazelcast.annotation.listener.EntryAdded;
import com.hazelcast.annotation.listener.EntryListener;
import com.hazelcast.annotation.listener.EntryRemoved;
import com.hazelcast.common.EntryListenerTypeEnum;
import com.hazelcast.core.EntryEvent;
import com.hazelcast.core.HazelcastInstance;

/**
 * Test Entry Listener Class
 *
 * @author Eren Avsarogullari
 * @author Yusuf Soysal
 * @since 17 March 2013
 * @version 1.0.0
 *
 */
@EntryListener(name = {"testMap", "testMap2"}, type=EntryListenerTypeEnum.MAP, needsValue=true)
@HazelcastAware
public class TestMapEntryListener {

    @HZInstance("MyHazelcastInstance")
    private HazelcastInstance instance;

	@EntryAdded
	public void entryAdded(EntryEvent event) {
		System.out.println("MAP - Entry is added " + event.toString() + " instance = " + instance);
	}
	
	@EntryRemoved
	public void entryRemoved(EntryEvent event) {
		System.out.println("MAP - Entry is remowed " + event.toString() + " instance = " + instance);
	}
}
