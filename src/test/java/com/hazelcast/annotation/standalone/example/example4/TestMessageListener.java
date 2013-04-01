package com.hazelcast.annotation.standalone.example.example4;

import com.hazelcast.annotation.HazelcastAware;
import com.hazelcast.annotation.listener.MessageListener;
import com.hazelcast.annotation.listener.OnMessage;
import com.hazelcast.common.MessageListenerTypeEnum;
import com.hazelcast.core.Message;

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
	
	@OnMessage
	public void entryAdded(Message message) {
		System.out.println("TOPIC - Message is added. Message Object : " + message.getMessageObject() + ", Source : " + message.getSource());
	}
}
