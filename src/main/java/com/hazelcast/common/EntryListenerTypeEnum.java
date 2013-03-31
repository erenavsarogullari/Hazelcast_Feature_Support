package com.hazelcast.common;

import java.util.Map;

import com.hazelcast.core.MultiMap;

public enum EntryListenerTypeEnum {

	MAP(Map.class), 
	MULTI_MAP(MultiMap.class);
	
	private Class entryListenerType;

	private EntryListenerTypeEnum(Class entryListenerType) {
		this.entryListenerType = entryListenerType;
	}

	public Class getEntryListenerType() {
		return entryListenerType;
	}
}
