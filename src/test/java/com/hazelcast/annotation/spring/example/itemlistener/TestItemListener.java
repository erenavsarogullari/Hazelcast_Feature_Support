package com.hazelcast.annotation.spring.example.itemlistener;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import com.hazelcast.annotation.HazelcastAware;
import com.hazelcast.annotation.listener.ItemAdded;
import com.hazelcast.annotation.listener.ItemListener;
import com.hazelcast.annotation.listener.ItemRemoved;
import com.hazelcast.common.ItemListenerTypeEnum;
import com.hazelcast.core.ItemEvent;

@HazelcastAware
@ItemListener(name ={"testList"}, type=ItemListenerTypeEnum.LIST, needsValue=true)
public class TestItemListener {

	private static final Logger logger = Logger.getLogger(TestItemListener.class);
	
	@Autowired
	User user;
	
	@ItemAdded
	public void itemAdded(ItemEvent event) {
		logger.debug("Item is added. " + event.toString());
		logger.debug("Autowired property is coming. " + user);
	}
	
	@ItemRemoved
	public void itemRemoved(ItemEvent event) {
		logger.debug("Item is removed. " + event.toString());
	}	
	
}
