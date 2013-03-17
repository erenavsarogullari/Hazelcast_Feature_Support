package com.hazelcast.common;

public enum ItemTypeEnum {

	LIST("L"), 
	SET("S"), 
	QUEUE("Q");
	
	private String itemType;

	private ItemTypeEnum(String itemType) {
		this.itemType = itemType;
	}

	public String getItemType() {
		return itemType;
	}
	
}
