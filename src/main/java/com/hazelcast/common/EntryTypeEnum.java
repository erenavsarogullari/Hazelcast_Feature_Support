package com.hazelcast.common;

public enum EntryTypeEnum {

	MAP("MAP"), 
	MULTI_MAP("MULTI_MAP");
	
	private String entryType;

	private EntryTypeEnum(String entryType) {
		this.entryType = entryType;
	}

	public String getEntryType() {
		return entryType;
	}
	
}
