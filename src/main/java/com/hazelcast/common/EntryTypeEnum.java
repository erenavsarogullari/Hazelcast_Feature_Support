package com.hazelcast.common;

/**
 * Hazelcast EntryTypeEnums
 *
 * @author Eren Avsarogullari
 * @author Yusuf Soysal
 * @since 17 March 2013
 * @version 1.0.0
 *
 */
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
