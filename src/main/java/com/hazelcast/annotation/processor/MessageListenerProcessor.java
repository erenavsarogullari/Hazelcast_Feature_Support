package com.hazelcast.annotation.processor;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.Set;

import com.hazelcast.annotation.builder.HZAware;
import com.hazelcast.annotation.builder.HazelcastAnnotationProcessor;
import com.hazelcast.annotation.listener.MessageListener;
import com.hazelcast.annotation.listener.OnMessage;
import com.hazelcast.core.HazelcastInstance;
import com.hazelcast.core.ITopic;
import com.hazelcast.listener.proxy.MessageListenerProxy;
import com.hazelcast.srv.IHazelcastService;

/**
 * Hazelcast EntryListener Annotation Processor
 *
 * @author Eren Avsarogullari
 * @author Yusuf Soysal
 * @version 1.0.0
 * @since 17 March 2013
 */
public class MessageListenerProcessor implements HazelcastAnnotationProcessor {


    @Override
    public boolean canBeProcessedMoreThanOnce() {
        return true;
    }

    @Override
    public void process(IHazelcastService hazelcastService, Object obj, Annotation annotation) {
    	createMessageProcessor(hazelcastService, obj.getClass(), obj, annotation);
    }

    @Override
    public void process(IHazelcastService hazelcastService, Class<?> clazz, Annotation annotation) {
        createMessageProcessor(hazelcastService, clazz, null, annotation);
    }

    public void createMessageProcessor(IHazelcastService hazelcastService, Class<?> clazz, Object obj, Annotation annotation) {
        Method onMessage = null;
        int numberOfMethods = 0;

        for (Method method : clazz.getMethods()) {
            Annotation[] annotations = method.getAnnotations();
            for (Annotation tempAnnotation : annotations) {
                if (tempAnnotation.annotationType() == MessageListenerEnum.ON_MESSAGE.getMessageType()) {
                	onMessage = method;
                    ++numberOfMethods;
                } 
            }
        }

        if (numberOfMethods > 0) {
        	addMessageListener(hazelcastService, clazz, obj, annotation, onMessage);
        }
    }

    private void addMessageListener(IHazelcastService hazelcastService, Class<?> clazz, Object obj, Annotation annotation, Method onMessage) {
        if (obj == null) {
            obj = HZAware.initialize(clazz);
        }

        Set<HazelcastInstance> allHazelcastInstances = hazelcastService.getAllHazelcastInstances();

        MessageListener listener = (MessageListener) annotation;

		for (HazelcastInstance instance : allHazelcastInstances) {

			MessageListenerProxy messageListenerProxy = new MessageListenerProxy(obj, onMessage);

			String[] distributedObjectNames = listener.name();
			for (String distributedObjectName : distributedObjectNames) {
				if(listener.type().getMessageListenerType() == ITopic.class) {
					ITopic topic = instance.getTopic(distributedObjectName);
					if (topic != null) {
						topic.addMessageListener(messageListenerProxy);
					}
				}				
			}
		}
    }

    private enum MessageListenerEnum {

        ON_MESSAGE(OnMessage.class);

        private Class<?> messageListenerType;

        private MessageListenerEnum(Class<?> messageListenerType) {
            this.messageListenerType = messageListenerType;
        }

        public Class<?> getMessageType() {
            return messageListenerType;
        }

    }

}
