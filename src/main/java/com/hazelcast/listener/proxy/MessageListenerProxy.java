package com.hazelcast.listener.proxy;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import com.hazelcast.common.HazelcastExtraException;
import com.hazelcast.core.EntryEvent;
import com.hazelcast.core.Message;
import com.hazelcast.core.MessageListener;


/**
 * Hazelcast EntryListener Proxy
 *
 * @author Yusuf Soysal
 * @author Eren Avsarogullari
 * @since 17 March 2013
 * @version 1.0.0
 *
 */
public class MessageListenerProxy implements MessageListener {

	private Object target;
	private Method onMessage;
	
	public MessageListenerProxy(Object target, Method onMessage) {
		this.target = target;
		this.onMessage = onMessage;
	}

	@Override
	public void onMessage(Message message) {
		try {
			if(onMessage != null) {
				onMessage.invoke(target, message);
			}
		} catch (IllegalArgumentException e) {
            throw new HazelcastExtraException("Method signature is incorrect for " + target.getClass().getName() + " method " + onMessage.getName(), e);
		} catch (IllegalAccessException e) {
            throw new HazelcastExtraException("Cannot access " + target.getClass().getName() + " method " + onMessage.getName(), e);
		} catch (InvocationTargetException e) {
            throw new HazelcastExtraException("Exception during processing  " + target.getClass().getName() + " method " + onMessage.getName(), e);
		}
	}

}
