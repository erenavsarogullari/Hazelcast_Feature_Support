package com.hazelcast.annotation.processor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Set;

import com.hazelcast.annotation.builder.HZAware;
import com.hazelcast.annotation.builder.HazelcastAnnotationProcessor;
import com.hazelcast.annotation.listener.ItemAdded;
import com.hazelcast.annotation.listener.ItemListener;
import com.hazelcast.annotation.listener.ItemRemoved;
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
    public boolean canBeProcessedMoreThanOnce() {
        return true;
    }

    @Override
    public void process(IHazelcastService hazelcastService, Object obj, Annotation annotation) {
        createItemListener(hazelcastService, obj.getClass(), obj, annotation);
    }

    @Override
	public Object process(IHazelcastService hazelcastService, Class<?> clazz, Annotation annotation) {
        return createItemListener(hazelcastService, clazz, null, annotation);
	}

    private Object createItemListener(IHazelcastService hazelcastService, Class<?> clazz, Object obj, Annotation annotation){
        Object returnVal = null;

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
            returnVal = addItemListener(hazelcastService, clazz, obj, annotation, itemAdded, itemRemoved);
        }

        return returnVal;
    }
	
	private Object addItemListener(IHazelcastService hazelcastService, Class<?> clazz, Object obj, Annotation annotation, Method itemAdded, Method itemRemoved) {
		Object returnVal = null;

		if( obj == null ){
            obj = HZAware.initialize(clazz);
            returnVal = obj;
        }

        Set<HazelcastInstance> allHazelcastInstances = hazelcastService.getAllHazelcastInstances();

        ItemListener listener = (ItemListener) annotation;

		for (HazelcastInstance instance : allHazelcastInstances) {

			ItemListenerProxy itemListenerProxy = new ItemListenerProxy(obj, itemAdded, itemRemoved);

			String[] distributedObjectNames = listener.distributedObjectName();
			for (String distributedObjectName : distributedObjectNames) {
				IList list = instance.getList(distributedObjectName);
				if (list != null) {
					list.addItemListener(itemListenerProxy, listener.needsValue());
				}

				IQueue queue = instance.getQueue(distributedObjectName);
				if (queue != null) {
					queue.addItemListener(itemListenerProxy, listener.needsValue());
				}

				ISet set = instance.getSet(distributedObjectName);
				if (set != null) {
					set.addItemListener(itemListenerProxy, listener.needsValue());
				}
			}
		}

        return returnVal;
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
