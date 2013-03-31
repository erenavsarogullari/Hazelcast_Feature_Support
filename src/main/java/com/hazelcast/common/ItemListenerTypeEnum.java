package com.hazelcast.common;

import java.util.List;
import java.util.Queue;
import java.util.Set;

public enum ItemListenerTypeEnum {

	LIST(List.class), 
	SET(Set.class),
	QUEUE(Queue.class);
	
	private Class itemListenerType;

	private ItemListenerTypeEnum(Class itemListenerType) {
		this.itemListenerType = itemListenerType;
	}

	public Class getItemListenerType() {
		return itemListenerType;
	}
}
