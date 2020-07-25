package com.antelopesystem.crudframework.utils.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Shani on 15/08/2018.
 */
public class ReflectionUtils extends org.springframework.util.ReflectionUtils {

	public static List<Field> getFields(Class type) {
		List<Field> fields = new ArrayList<>();

		Class classToGetFields = type;
		while(classToGetFields != null) {
			fields.addAll(Arrays.asList(classToGetFields.getDeclaredFields()));
			classToGetFields = classToGetFields.getSuperclass();
		}

		return fields;
	}

	public static List<Method> getMethods(Class type) {
		List<Method> methods = new ArrayList<>();

		Class classToGetMethods = type;
		while(classToGetMethods != null) {
			methods.addAll(Arrays.asList(classToGetMethods.getDeclaredMethods()));
			classToGetMethods = classToGetMethods.getSuperclass();
		}

		return methods;
	}


	public static <T> T instantiateClass(Class<T> clazz) {
		try {
			return clazz.newInstance();
		} catch(InstantiationException e) {
			throw new IllegalStateException("Instantiation exception: " + e.getMessage());
		} catch(IllegalAccessException e) {
			throw new IllegalStateException("Illegal access exception: " + e.getMessage());
		}
	}
}
