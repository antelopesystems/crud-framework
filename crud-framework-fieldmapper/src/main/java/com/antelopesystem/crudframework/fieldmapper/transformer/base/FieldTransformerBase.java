package com.antelopesystem.crudframework.fieldmapper.transformer.base;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.HashMap;
import java.util.Map;

public abstract class FieldTransformerBase<T, E> implements FieldTransformer<T, E> {

	private static Map<Class<?>, Class<?>> primitiveMap;

	{
		primitiveMap = new HashMap<>();
		primitiveMap.put(boolean.class, Boolean.class);
		primitiveMap.put(byte.class, Byte.class);
		primitiveMap.put(short.class, Short.class);
		primitiveMap.put(char.class, Character.class);
		primitiveMap.put(int.class, Integer.class);
		primitiveMap.put(long.class, Long.class);
		primitiveMap.put(float.class, Float.class);
		primitiveMap.put(double.class, Double.class);
	}

	@Override
	public final E transform(Field fromField, Field toField, T originalValue, Object fromObject, Object toObject) {
		Class<T> tClazz;
		Class<E> eClazz;
		Object fromClazz = ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0];
		if(fromClazz instanceof ParameterizedType) {
			tClazz = (Class<T>) ((ParameterizedType) fromClazz).getRawType();
		} else {
			tClazz = (Class<T>) fromClazz;
		}

		Object toClazz = ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[1];
		if(toClazz instanceof ParameterizedType) {
			eClazz = (Class<E>) ((ParameterizedType) toClazz).getRawType();
		} else {
			eClazz = (Class<E>) toClazz;
		}

		if(!tClazz.isAssignableFrom(getActualClass(fromField.getType()))) {
			throw new IllegalStateException(fromField.toString() + " - fromField type [ " + fromField.getType().getSimpleName() + " ] does not match transformer fromType [ " + tClazz.getSimpleName() + " ]");
		}

		if(!eClazz.isAssignableFrom(getActualClass(toField.getType()))) {
			throw new IllegalStateException(toField.toString() + " - toField type [ " + toField.getType().getSimpleName() + " ] does not match transformer toType [ " + eClazz.getSimpleName() + " ]");
		}

		return innerTransform(fromField, toField, originalValue, fromObject, toObject);
	}

	protected abstract E innerTransform(Field fromField, Field toField, T originalValue, Object fromObject, Object toObject);

	private static Class<?> getActualClass(Class<?> clazz) {
		if(clazz.isPrimitive()) {
			return primitiveMap.get(clazz);
		}

		return clazz;
	}
}
