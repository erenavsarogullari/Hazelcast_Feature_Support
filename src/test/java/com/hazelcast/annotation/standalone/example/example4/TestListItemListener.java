package com.hazelcast.annotation.standalone.example.example4;

import com.hazelcast.annotation.HazelcastAware;
import com.hazelcast.annotation.listener.ItemAdded;
import com.hazelcast.annotation.listener.ItemListener;
import com.hazelcast.common.ItemListenerTypeEnum;
import com.hazelcast.core.ItemEvent;
import org.apache.log4j.Logger;

/**
 * Test Item Listener Class
 *
 * @author Eren Avsarogullari
 * @author Yusuf Soysal
 * @since 17 March 2013
 * @version 1.0.0
 *
 */
@ItemListener(name ={"testList"}, type=ItemListenerTypeEnum.LIST, needsValue=true)
public class TestListItemListener {

    private static final Logger logger = Logger.getLogger(TestListItemListener.class);

	@ItemAdded
	public void myListIsGrowing(ItemEvent event) {
		logger.info("LIST - Item is really added " + event.toString());
	}
}
