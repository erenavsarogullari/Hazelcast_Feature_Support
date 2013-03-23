package com.hazelcast.common;

/**
 * Hazelcast ItemTypeEnums
 *
 * @author Eren Avsarogullari
 * @author Yusuf Soysal
 * @since 17 March 2013
 * @version 1.0.0
 *
 */
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
