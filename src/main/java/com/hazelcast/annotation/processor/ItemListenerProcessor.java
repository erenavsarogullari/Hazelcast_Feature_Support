package com.hazelcast.annotation.processor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;

import com.hazelcast.annotation.ItemAdded;
import com.hazelcast.annotation.ItemListener;
import com.hazelcast.annotation.ItemRemoved;
import com.hazelcast.annotation.scanner.HazelcastAnnotationProcessor;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IList;
import com.hazelcast.core.IQueue;
import com.hazelcast.core.ISet;
import com.hazelcast.listener.proxy.ItemListenerProxy;

public class ItemListenerProcessor implements HazelcastAnnotationProcessor {

	@Override
	public void process(Class<?> clazz, Annotation annotation) {
		Method itemAdded = null, itemRemoved = null;
		int numberOfMethods = 0;

		for (Method method : clazz.getMethods()) {
			Annotation[] annotations = method.getAnnotations();
			for (Annotation tempAnnotation : annotations) {
				if (tempAnnotation.annotationType() == ItemListenerEnum.ITEM_ADDED.getItemType()) {
					itemAdded = method;
					++numberOfMethods;
				} else if (tempAnnotation.annotationType() == ItemListenerEnum.ITEM_REMOVED.getItemType()) {
					itemRemoved = method;
					++numberOfMethods;
				}
			}
		}

		if (numberOfMethods > 0) {
			addItemListener(clazz, annotation, itemAdded, itemRemoved);
		}

	}
	
	private void addItemListener(Class<?> clazz, Annotation annotation, Method itemAdded, Method itemRemoved) {
		try {
			ItemListenerProxy itemListenerProxy = new ItemListenerProxy(clazz.newInstance(), itemAdded, itemRemoved);

			Set<HazelcastInstance> allHazelcastInstances = Hazelcast.getAllHazelcastInstances();

			for (HazelcastInstance instance : allHazelcastInstances) {
				ItemListener listener = (ItemListener) annotation;

				switch (listener.type()) {

				case LIST:
					IList list = instance.getList(listener.distributedObjectName());
					if (list != null) {
						list.addItemListener(itemListenerProxy, listener.needsValue());
					}
					break;

				case QUEUE:
					IQueue queue = instance.getQueue(listener.distributedObjectName());
					if (queue != null) {
						queue.addItemListener(itemListenerProxy, listener.needsValue());
					}
					break;

				case SET:
					ISet set = instance.getSet(listener.distributedObjectName());
					if (set != null) {
						set.addItemListener(itemListenerProxy, listener.needsValue());
					}
					break;
				}
			}
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
	}

	private enum ItemListenerEnum {

		ITEM_ADDED(ItemAdded.class), 
		ITEM_REMOVED(ItemRemoved.class);

		private Class<?> itemListenerType;

		private ItemListenerEnum(Class<?> itemListenerType) {
			this.itemListenerType = itemListenerType;
		}

		public Class<?> getItemType() {
			return itemListenerType;
		}

	}

}
