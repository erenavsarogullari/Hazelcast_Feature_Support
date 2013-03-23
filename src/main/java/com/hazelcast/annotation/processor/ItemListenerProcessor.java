package com.hazelcast.annotation.processor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;

import com.hazelcast.annotation.ItemAdded;
import com.hazelcast.annotation.ItemListener;
import com.hazelcast.annotation.ItemRemoved;
import com.hazelcast.annotation.builder.HazelcastAnnotationProcessor;
import com.hazelcast.common.ItemTypeEnum;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.IList;
import com.hazelcast.core.IQueue;
import com.hazelcast.core.ISet;
import com.hazelcast.listener.proxy.ItemListenerProxy;
import com.hazelcast.srv.IHazelcastService;

/**
 * Hazelcast ItemListener Annotation Interface
 *
 * @author Yusuf Soysal
 * @author Eren Avsarogullari
 * @since 17 March 2013
 * @version 1.0.0
 *
 */
public class ItemListenerProcessor implements HazelcastAnnotationProcessor {

	@Override
	public void process(IHazelcastService hazelcastService, Class<?> clazz, Annotation annotation) {
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
			addItemListener(hazelcastService, clazz, annotation, itemAdded, itemRemoved);
		}

	}
	
	private void addItemListener(IHazelcastService hazelcastService, Class<?> clazz, Annotation annotation, Method itemAdded, Method itemRemoved) {
		
		ItemListenerProxy itemListenerProxy = null;
		
		try {

			Set<HazelcastInstance> allHazelcastInstances = hazelcastService.getAllHazelcastInstances();

			ItemListener listener = (ItemListener) annotation;
			
			for (HazelcastInstance instance : allHazelcastInstances) {				
				
				String[] distributedObjectNames = null;
				ItemTypeEnum[] types = listener.type();
				
				for (ItemTypeEnum type : types) {
					
					itemListenerProxy = new ItemListenerProxy(clazz.newInstance(), itemAdded, itemRemoved);
					
					switch (type) {

					case LIST:
						distributedObjectNames = listener.distributedObjectName();
						for (String distributedObjectName : distributedObjectNames) {
							IList list = instance.getList(distributedObjectName);
							if (list != null) {
								list.addItemListener(itemListenerProxy, listener.needsValue());
							}
						}
						break;

					case QUEUE:
						distributedObjectNames = listener.distributedObjectName();
						for (String distributedObjectName : distributedObjectNames) {
							IQueue queue = instance.getQueue(distributedObjectName);
							if (queue != null) {
								queue.addItemListener(itemListenerProxy, listener.needsValue());
							}
						}
						break;

					case SET:
						distributedObjectNames = listener.distributedObjectName();
						for (String distributedObjectName : distributedObjectNames) {
							ISet set = instance.getSet(distributedObjectName);
							if (set != null) {
								set.addItemListener(itemListenerProxy, listener.needsValue());
							}
						}
						break;
					}
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
