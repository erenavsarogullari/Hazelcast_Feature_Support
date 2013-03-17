package com.hazelcast.annotation.processor;

import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.RoundEnvironment;
import javax.annotation.processing.SupportedAnnotationTypes;
import javax.annotation.processing.SupportedSourceVersion;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.TypeElement;

import com.hazelcast.common.SystemConstants;

@SupportedAnnotationTypes(SystemConstants.MEMBERSHIP_LISTENER_DEFINITION)
@SupportedSourceVersion(SourceVersion.RELEASE_6)
public class MembershipListenerProcessor extends AbstractProcessor {

	@Override
	public boolean process(Set<? extends TypeElement> annotations, RoundEnvironment roundEnv) {
		return false;
	}
	
	

}
