package com.qa.choonz.util;

import static org.springframework.beans.BeanUtils.copyProperties;

import java.beans.PropertyDescriptor;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

public class SpringBeanUtil {

	public static void mergeNotNull(Object source, Object target) {
		copyProperties(source, target, getNullPropertyNames(source));
	}

	private static String[] getNullPropertyNames(Object source) {
		final BeanWrapper wrappedSourceObject = new BeanWrapperImpl(source);

		Set<String> propertyNames = new HashSet<>();
		for (PropertyDescriptor propertyDescriptors : wrappedSourceObject.getPropertyDescriptors()) {
			if (wrappedSourceObject.getPropertyValue(propertyDescriptors.getName()) == null)
				propertyNames.add(propertyDescriptors.getName());
		}
		return propertyNames.toArray(new String[propertyNames.size()]);
	}

	private SpringBeanUtil() {
	}

}