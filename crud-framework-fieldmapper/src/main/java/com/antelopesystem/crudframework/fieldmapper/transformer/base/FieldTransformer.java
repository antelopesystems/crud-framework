package com.antelopesystem.crudframework.fieldmapper.transformer.base;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.springframework.core.GenericTypeResolver;

import java.lang.reflect.Field;

public interface FieldTransformer<FromType, ToType> {

	/**
	 * Whether or not the transformer is considered default for the [FromType]-[ToType] pair
	 */
	default boolean isDefault() {
		return false;
	}

	default Class<FromType> fromType() {
		return (java.lang.Class<FromType>) GenericTypeResolver.resolveTypeArguments(getClass(), FieldTransformer.class)[0];
	}

	default Class<ToType> toType() {
		return (java.lang.Class<ToType>) GenericTypeResolver.resolveTypeArguments(getClass(), FieldTransformer.class)[1];
	}

	ToType transform(@NotNull Field fromField, @NotNull Field toField, @Nullable FromType originalValue, @NotNull Object fromObject, @NotNull Object toObject);
}
