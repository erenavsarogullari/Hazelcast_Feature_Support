package com.hazelcast.listener.proxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.hazelcast.common.HazelcastExtraException;
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
            throw new HazelcastExtraException("Method signature is incorrect for " + target.getClass().getName() + " method " + entryAdded.getName(), e);
		} catch (IllegalAccessException e) {
            throw new HazelcastExtraException("Cannot access " + target.getClass().getName() + " method " + entryAdded.getName(), e);
		} catch (InvocationTargetException e) {
            throw new HazelcastExtraException("Exception during processing  " + target.getClass().getName() + " method " + entryAdded.getName(), e);
		}
	}

	@Override
	public void entryEvicted(EntryEvent ee) {
		try {
			if(entryEvicted != null) {
				entryEvicted.invoke(target, ee);
			}
        } catch (IllegalArgumentException e) {
            throw new HazelcastExtraException("Method signature is incorrect for " + target.getClass().getName() + " method " + entryEvicted.getName(), e);
        } catch (IllegalAccessException e) {
            throw new HazelcastExtraException("Cannot access " + target.getClass().getName() + " method " + entryEvicted.getName(), e);
        } catch (InvocationTargetException e) {
            throw new HazelcastExtraException("Exception during processing  " + target.getClass().getName() + " method " + entryEvicted.getName(), e);
        }
	}

	@Override
	public void entryRemoved(EntryEvent ee) {
		try {
			if(entryRemoved != null) {
				entryRemoved.invoke(target, ee);
			}
        } catch (IllegalArgumentException e) {
            throw new HazelcastExtraException("Method signature is incorrect for " + target.getClass().getName() + " method " + entryRemoved.getName(), e);
        } catch (IllegalAccessException e) {
            throw new HazelcastExtraException("Cannot access " + target.getClass().getName() + " method " + entryRemoved.getName(), e);
        } catch (InvocationTargetException e) {
            throw new HazelcastExtraException("Exception during processing  " + target.getClass().getName() + " method " + entryRemoved.getName(), e);
        }
	}

	@Override
	public void entryUpdated(EntryEvent ee) {
		try {
			if(entryUpdated != null) {
				entryUpdated.invoke(target, ee);
			}
        } catch (IllegalArgumentException e) {
            throw new HazelcastExtraException("Method signature is incorrect for " + target.getClass().getName() + " method " + entryUpdated.getName(), e);
        } catch (IllegalAccessException e) {
            throw new HazelcastExtraException("Cannot access " + target.getClass().getName() + " method " + entryUpdated.getName(), e);
        } catch (InvocationTargetException e) {
            throw new HazelcastExtraException("Exception during processing  " + target.getClass().getName() + " method " + entryUpdated.getName(), e);
        }
	}

}
