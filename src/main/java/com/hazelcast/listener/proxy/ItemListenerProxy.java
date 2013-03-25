package com.hazelcast.listener.proxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.hazelcast.common.HazelcastExtraException;
import com.hazelcast.core.ItemEvent;
import com.hazelcast.core.ItemListener;

/**
 * Hazelcast ItemListener Proxy
 *
 * @author Yusuf Soysal
 * @author Eren Avsarogullari
 * @since 17 March 2013
 * @version 1.0.0
 *
 */
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
            throw new HazelcastExtraException("Method signature is incorrect for " + target.getClass().getName() + " method " + itemAdded.getName(), e);
        } catch (IllegalAccessException e) {
            throw new HazelcastExtraException("Cannot access " + target.getClass().getName() + " method " + itemAdded.getName(), e);
        } catch (InvocationTargetException e) {
            throw new HazelcastExtraException("Exception during processing  " + target.getClass().getName() + " method " + itemAdded.getName(), e);
        }
	}

	@Override
	public void itemRemoved(ItemEvent ie) {
		try {
			if(itemRemoved != null) {
				itemRemoved.invoke(target, ie);
			}
        } catch (IllegalArgumentException e) {
            throw new HazelcastExtraException("Method signature is incorrect for " + target.getClass().getName() + " method " + itemRemoved.getName(), e);
        } catch (IllegalAccessException e) {
            throw new HazelcastExtraException("Cannot access " + target.getClass().getName() + " method " + itemRemoved.getName(), e);
        } catch (InvocationTargetException e) {
            throw new HazelcastExtraException("Exception during processing  " + target.getClass().getName() + " method " + itemRemoved.getName(), e);
        }
	}

}
