package com.antelopesystem.crudframework.fieldmapper.transformer.base;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.core.GenericTypeResolver;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public abstract class FieldTransformerBase<FromType, ToType> implements FieldTransformer<FromType, ToType> {

	private static final Map<Class<?>, Class<?>> PRIMITIVES;

	static {
		PRIMITIVES = new HashMap<>();
		PRIMITIVES.put(boolean.class, Boolean.class);
		PRIMITIVES.put(byte.class, Byte.class);
		PRIMITIVES.put(short.class, Short.class);
		PRIMITIVES.put(char.class, Character.class);
		PRIMITIVES.put(int.class, Integer.class);
		PRIMITIVES.put(long.class, Long.class);
		PRIMITIVES.put(float.class, Float.class);
		PRIMITIVES.put(double.class, Double.class);
	}

	@Override
	public ToType transform(@NotNull Field fromField, @NotNull Field toField, @Nullable FromType originalValue, @NotNull Object fromObject, @NotNull Object toObject) {
		Class<?>[] generics = GenericTypeResolver.resolveTypeArguments(getClass(), FieldTransformerBase.class);
		Class<FromType> fromClazz = (Class<FromType>) getActualClass(generics[0]);
		Class<ToType> eClazz = (Class<ToType>) getActualClass(generics[1]);

		if(!fromClazz.isAssignableFrom(getActualClass(fromField.getType()))) {
			throw new IllegalStateException(fromField.toString() + " - fromField type [ " + fromField.getType().getSimpleName() + " ] does not match transformer fromType [ " + fromClazz.getSimpleName() + " ]");
		}

		if(!eClazz.isAssignableFrom(getActualClass(toField.getType()))) {
			throw new IllegalStateException(toField.toString() + " - toField type [ " + toField.getType().getSimpleName() + " ] does not match transformer toType [ " + eClazz.getSimpleName() + " ]");
		}

		return innerTransform(fromField, toField, originalValue, fromObject, toObject);
	}

	protected abstract ToType innerTransform(@NotNull Field fromField, @NotNull Field toField, @Nullable FromType originalValue, @NotNull Object fromObject, @NotNull Object toObject);

	private static Class<?> getActualClass(Class<?> clazz) {
		if(clazz.isPrimitive()) {
			return PRIMITIVES.get(clazz);
		}

		return clazz;
	}
}
