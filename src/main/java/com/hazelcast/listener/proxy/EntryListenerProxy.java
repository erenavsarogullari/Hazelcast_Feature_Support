package com.hazelcast.listener.proxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.hazelcast.core.EntryEvent;
import com.hazelcast.core.EntryListener;

/**
 * Hazelcast EntryListener Proxy
 *
 * @author Yusuf Soysal
 * @author Eren Avsarogullari
 * @since 17 March 2013
 * @version 1.0.0
 *
 */
public class EntryListenerProxy implements EntryListener {

	private Object target;
	private Method entryAdded;
	private Method entryRemoved;
	private Method entryUpdated;
	private Method entryEvicted;
	
	public EntryListenerProxy(Object target, Method entryAdded, Method entryRemoved, Method entryUpdated, Method entryEvicted) {
		this.target = target;
		this.entryAdded = entryAdded;
		this.entryRemoved = entryRemoved;
		this.entryUpdated = entryUpdated;
		this.entryEvicted = entryEvicted;
	}

	@Override
	public void entryAdded(EntryEvent ee) {
		try {
			if(entryAdded != null) {
				entryAdded.invoke(target, ee);
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
	public void entryEvicted(EntryEvent ee) {
		try {
			if(entryEvicted != null) {
				entryEvicted.invoke(target, ee);
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
	public void entryRemoved(EntryEvent ee) {
		try {
			if(entryRemoved != null) {
				entryRemoved.invoke(target, ee);
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
	public void entryUpdated(EntryEvent ee) {
		try {
			if(entryUpdated != null) {
				entryUpdated.invoke(target, ee);
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
