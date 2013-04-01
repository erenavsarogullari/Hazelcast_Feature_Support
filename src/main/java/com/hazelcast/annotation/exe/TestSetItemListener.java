package com.hazelcast.annotation.exe;

import com.hazelcast.annotation.listener.ItemAdded;
import com.hazelcast.annotation.listener.ItemListener;
import com.hazelcast.common.ItemListenerTypeEnum;
import com.hazelcast.core.ItemEvent;

/**
 * Test Item Listener Class
 *
 * @author Eren Avsarogullari
 * @author Yusuf Soysal
 * @since 17 March 2013
 * @version 1.0.0
 *
 */
@ItemListener(name ={"testSet", "testSet2"}, type=ItemListenerTypeEnum.SET, needsValue=true)
public class TestSetItemListener {	
	
	@ItemAdded
	public void itemAdded(ItemEvent event) {
		System.out.println("SET - Item is really added " + event.toString());
	}
}
