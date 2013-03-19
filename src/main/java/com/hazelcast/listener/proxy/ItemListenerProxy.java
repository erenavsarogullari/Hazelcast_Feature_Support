package com.hazelcast.listener.proxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.hazelcast.core.ItemEvent;
import com.hazelcast.core.ItemListener;

public class ItemListenerProxy implements ItemListener {

	private Object target;
	private Method itemAdded;
	private Method itemRemoved;
	
	public ItemListenerProxy(Object target, Method itemAdded, Method itemRemoved) {
		this.target = target;
		this.itemAdded = itemAdded;
		this.itemRemoved = itemRemoved;
	}

	@Override
	public void itemAdded(ItemEvent ie) {
		try {
			if(itemAdded != null) {
			   itemAdded.invoke(target, ie);
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void itemRemoved(ItemEvent ie) {
		try {
			if(itemRemoved != null) {
				itemRemoved.invoke(target, ie);
			}
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

}
