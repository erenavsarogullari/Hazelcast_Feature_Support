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
@ItemListener(name ={"testQueue", "testQueue2"}, type=ItemListenerTypeEnum.QUEUE, needsValue=true)
public class TestQueueItemListener {

    private static final Logger logger = Logger.getLogger(TestQueueItemListener.class);

	@ItemAdded
	public void itemAdded(ItemEvent event) {
		logger.info("QUEUE - Item is really added " + event.toString());
	}
}
