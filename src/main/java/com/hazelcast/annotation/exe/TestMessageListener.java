package com.hazelcast.annotation.exe;

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
@HazelcastAware
@MessageListener(name = {"testTopic", "testTopic2"}, type=MessageListenerTypeEnum.TOPIC)
public class TestMessageListener {	
	
	@OnMessage
	public void entryAdded(Message message) {
		System.out.println("Message is added. Message Object : " + message.getMessageObject() + ", Source : " + message.getSource());
	}
}
