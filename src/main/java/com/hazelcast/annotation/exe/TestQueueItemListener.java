package com.hazelcast.annotation.exe;

import com.hazelcast.annotation.HazelcastAware;
import com.hazelcast.annotation.listener.ItemAdded;
import com.hazelcast.annotation.listener.ItemListener;
import com.hazelcast.core.ItemEvent;

import com.hazelcast.common.ItemListenerTypeEnum;;
/**
 * Test Item Listener Class
 *
 * @author Eren Avsarogullari
 * @author Yusuf Soysal
 * @since 17 March 2013
 * @version 1.0.0
 *
 */
@HazelcastAware
@ItemListener(name ={"testQueue", "testQueue2"}, type=ItemListenerTypeEnum.QUEUE, needsValue=true)
public class TestQueueItemListener {	
	
	@ItemAdded
	public void itemAdded(ItemEvent event) {
		System.out.println("QUEUE - Item is really added " + event.toString());
	}
}
