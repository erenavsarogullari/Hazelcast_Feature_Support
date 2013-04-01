package com.hazelcast.annotation.spring.example.messagelistener;

import org.apache.log4j.Logger;

import com.hazelcast.annotation.listener.MessageListener;
import com.hazelcast.annotation.listener.OnMessage;
import com.hazelcast.common.MessageListenerTypeEnum;
import com.hazelcast.core.Message;

@MessageListener(name ={"testTopic", "testTopic2"}, type=MessageListenerTypeEnum.TOPIC)
public class TestTopicMessageListener {

	private static final Logger logger = Logger.getLogger(TestTopicMessageListener.class);
	
	@OnMessage
	public void onMessage(Message message) {
		logger.debug("Message is added. " + message.getMessageObject());
	}	
	
}
