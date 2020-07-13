package com.antelopesystem.crudframework.fieldmapper.transformer.base;

import com.antelopesystem.crudframework.exception.tree.core.ErrorCode;
import com.antelopesystem.crudframework.fieldmapper.transformer.exception.TransformationException;

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
	public final E transform(Field fromField, Field toField, T originalValue) {
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
			throw new TransformationException()
					.withDisplayMessage(fromField.toString() + " - fromField type [ " + fromField.getType().getSimpleName() + " ] does not match transformer fromType [ " + tClazz.getSimpleName() + " ]")
					.withErrorCode(ErrorCode.FieldTypeMismatch);
		}

		if(!eClazz.isAssignableFrom(getActualClass(toField.getType()))) {
			throw new TransformationException()
					.withDisplayMessage(toField.toString() + " - toField type [ " + toField.getType().getSimpleName() + " ] does not match transformer toType [ " + eClazz.getSimpleName() + " ]")
					.withErrorCode(ErrorCode.FieldTypeMismatch);
		}

		return innerTransform(fromField, toField, originalValue);
	}

	protected abstract E innerTransform(Field fromField, Field toField, T originalValue);

	private static Class<?> getActualClass(Class<?> clazz) {
		if(clazz.isPrimitive()) {
			return primitiveMap.get(clazz);
		}

		return clazz;
	}
}
