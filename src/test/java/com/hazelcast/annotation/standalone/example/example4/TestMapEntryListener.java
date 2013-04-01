package com.hazelcast.annotation.standalone.example.example4;

import com.hazelcast.annotation.HazelcastAware;
import com.hazelcast.annotation.data.HZInstance;
import com.hazelcast.annotation.listener.EntryAdded;
import com.hazelcast.annotation.listener.EntryListener;
import com.hazelcast.annotation.listener.EntryRemoved;
import com.hazelcast.common.EntryListenerTypeEnum;
import com.hazelcast.core.EntryEvent;
import com.hazelcast.core.HazelcastInstance;
import org.apache.log4j.Logger;

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

    private static final Logger logger = Logger.getLogger(TestMapEntryListener.class);

    @HZInstance
    private HazelcastInstance instance;

	@EntryAdded
	public void entryAdded(EntryEvent event) {
		logger.info("MAP - Entry is added " + event.toString());
        if( instance != null ){
            logger.info("Look! Injection also work on listeners too!");
        } else {
            logger.error("Problem occured while injecting instance to listener. Shame :(");
        }
	}
	
	@EntryRemoved
	public void entryRemoved(EntryEvent event) {
		logger.info("MAP - Entry is remowed " + event.toString());
	}
}
