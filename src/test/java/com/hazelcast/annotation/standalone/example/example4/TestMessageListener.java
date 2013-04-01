package com.hazelcast.annotation.standalone.example.example4;

import com.hazelcast.annotation.HazelcastAware;
import com.hazelcast.annotation.data.HZInstance;
import com.hazelcast.annotation.listener.MessageListener;
import com.hazelcast.annotation.listener.OnMessage;
import com.hazelcast.common.MessageListenerTypeEnum;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.Message;
import org.apache.log4j.Logger;

/**
 * Test Entry Listener Class
 *
 * @author Eren Avsarogullari
 * @author Yusuf Soysal
 * @since 28 March 2013
 * @version 1.0.0
 *
 */
@MessageListener(name = {"testTopic", "testTopic2"}, type=MessageListenerTypeEnum.TOPIC)
public class TestMessageListener {

    private static final Logger logger = Logger.getLogger(TestMessageListener.class);

	@OnMessage
	public void entryAdded(Message message) {
		logger.info("TOPIC - Message is added. Message Object : " + message.getMessageObject() + ", Source : " + message.getSource());
	}
}
