package com.hazelcast.spring;

import com.hazelcast.annotation.builder.HazelcastAnnotationBuilder;
import com.hazelcast.common.ObjectCreator;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

/**
 * Date: 31/03/2013 12:44
 * Author Yusuf Soysal
 */
public class HazelcastSpringAnnotationSupport implements BeanDefinitionParser, ObjectCreator {

    private ParserContext parserContext;

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        this.parserContext = parserContext;
        // hzaware'leri bulup property'leri inject et. bunun icin springin yarattigi beanleri kullanmak lazim
        // listenerlari bulup bean olarak yarat. bunun icin aramayi hemen yapmak lazim

        findTypeAnnotations(element.getAttribute(SpringConstants.HZ_ATT_ANNOTATION_PACKAGE));


        return null;
    }

    public void findTypeAnnotations(String pck){
        HazelcastAnnotationBuilder builder = HazelcastAnnotationBuilder.getInstance();
        builder.setObjectCreator(this);
        SpringUtils.findClasses(pck, builder.getClasspathScanner());
        builder.fireEvents();
    }

    @Override
    public <T> T createInstance(Class<T> clz) throws IllegalAccessException, InstantiationException {
        return SpringUtils.registerNewBean(parserContext, clz);
    }
}
