package com.hazelcast.common;

public enum ItemTypeEnum {

	LIST("LIST"), 
	SET("SET"), 
	QUEUE("QUEUE");
	
	private String itemType;

	private ItemTypeEnum(String itemType) {
		this.itemType = itemType;
	}

	public String getItemType() {
		return itemType;
	}
	
}
