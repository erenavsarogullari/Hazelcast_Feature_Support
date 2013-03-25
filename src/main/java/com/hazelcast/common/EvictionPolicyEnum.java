package com.hazelcast.common;

public enum EvictionPolicyEnum {

	NONE("NONE"), 
	LRU("LRU"),
	LFU("LFU");
	
	private String evictionPolicy;

	private EvictionPolicyEnum(String evictionPolicy) {
		this.evictionPolicy = evictionPolicy;
	}

	public String getEvictionPolicy() {
		return evictionPolicy;
	}
}
