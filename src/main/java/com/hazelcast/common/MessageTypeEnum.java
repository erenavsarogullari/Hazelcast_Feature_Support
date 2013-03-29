package com.hazelcast.common;

/**
 * Hazelcast MessageTypeEnum
 *
 * @author Eren Avsarogullari
 * @author Yusuf Soysal
 * @since 28 March 2013
 * @version 1.0.0
 *
 */
public enum MessageTypeEnum {

	TOPIC("TOPIC");
	
	private String messageType;

	private MessageTypeEnum(String messageType) {
		this.messageType = messageType;
	}

	public String getMessageType() {
		return messageType;
	}
	
}
