package com.hazelcast.spring;

import org.springframework.beans.factory.xml.NamespaceHandlerSupport;

/**
 * Date: 31/03/2013 12:37
 * Author Yusuf Soysal
 */
public class HazelcastExtraNamespaceHandler extends NamespaceHandlerSupport {

    @Override
    public void init() {
        registerBeanDefinitionParser(SpringConstants.HZ_TAG_ANNOTATION_SUPPORT, new HazelcastSpringAnnotationSupport());
    }

}
