package com.hazelcast.spring;

import com.hazelcast.common.AnnotatedClass;
import com.hazelcast.common.Annotations;
import com.hazelcast.common.ClasspathScanEventListener;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.xml.BeanDefinitionParser;
import org.springframework.beans.factory.xml.ParserContext;
import org.w3c.dom.Element;

import com.hazelcast.annotation.builder.HazelcastAnnotationBuilder;
import com.hazelcast.common.ObjectCreator;
import com.hazelcast.spring.beans.ApplicationContextListener;
import com.hazelcast.spring.beans.SpringEventListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Date: 31/03/2013 12:44
 * Author Yusuf Soysal
 */
public class HazelcastSpringAnnotationSupport implements BeanDefinitionParser {

    private ParserContext parserContext;

    @Override
    public BeanDefinition parse(Element element, ParserContext parserContext) {
        this.parserContext = parserContext;
        // hzaware'leri bulup property'leri inject et. bunun icin springin yarattigi beanleri kullanmak lazim
        // listenerlari bulup bean olarak yarat. bunun icin aramayi hemen yapmak lazim

        SpringUtils.registerNewBean(parserContext, ApplicationContextListener.class);
        SpringUtils.registerNewBean(parserContext, SpringEventListener.class);

        findTypeAnnotations(element.getAttribute(SpringConstants.HZ_ATT_ANNOTATION_PACKAGE));

        return null;
    }

    public void findTypeAnnotations(String pck){
        HazelcastAnnotationBuilder builder = HazelcastAnnotationBuilder.getInstance();
        SpringUtils.findClasses(pck, new SpringClasspathScannerImpl());
        builder.fireEvents();
    }

    private class SpringClasspathScannerImpl implements ClasspathScanEventListener {

        private HazelcastAnnotationBuilder builder = HazelcastAnnotationBuilder.getInstance();

        @Override
        public void classFound(Class<?> clazz) {
            List<AnnotatedClass> supportedAnnotatedClassList = Annotations.SupportedAnnotation.getAnnotatedClassList(clazz);

            boolean needsRegister = false;

            for (AnnotatedClass supportedAnnotatedClass : supportedAnnotatedClassList) {

                Annotations.SupportedAnnotation supported = supportedAnnotatedClass.getSupportedAnnotation();

                if( supported == Annotations.SupportedAnnotation.CONFIGURATION ){
                    supported.getProcessor().process(builder.getHazelcastService(), supportedAnnotatedClass.getClazz(), supportedAnnotatedClass.getAnnotation() );
                } else if( supported != Annotations.SupportedAnnotation.HAZELCASTAWARE ) {
                    needsRegister = true;
                }
            }

            if( needsRegister ){
                SpringUtils.registerNewBean(parserContext, clazz);
            }
        }
    }
}
