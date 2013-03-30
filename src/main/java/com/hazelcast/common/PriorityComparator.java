package com.hazelcast.common;

import java.util.Comparator;

import com.hazelcast.common.Annotations.SupportedAnnotation;

/**
 * Annotation Scanning Priority Comparator
 *
 * @author Eren Avsarogullari
 * @author Yusuf Soysal
 * @since 28 March 2013
 * @version 1.0.0
 *
 */
public class PriorityComparator implements Comparator<Annotations.SupportedAnnotation> {

    @Override
	public int compare(SupportedAnnotation sa1, SupportedAnnotation sa2) {
		if (sa1.getPriority() < sa2.getPriority()) {
			return -1;
		} else if (sa1.getPriority() == sa2.getPriority()) {
			return 0;
		} else {
			return 1;
		}
	}
    
}
