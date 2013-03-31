package com.hazelcast.common;

import com.hazelcast.core.ITopic;

public enum MessageListenerTypeEnum {

	TOPIC(ITopic.class);
	
	private Class messageListenerType;

	private MessageListenerTypeEnum(Class messageListenerType) {
		this.messageListenerType = messageListenerType;
	}

	public Class getMessageListenerType() {
		return messageListenerType;
	}
}
