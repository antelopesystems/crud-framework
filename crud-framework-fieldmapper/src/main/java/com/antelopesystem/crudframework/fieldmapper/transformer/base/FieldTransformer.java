package com.antelopesystem.crudframework.fieldmapper.transformer.base;

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

	ToType transform(Field fromField, Field toField, FromType originalValue, Object fromObject, Object toObject);
}
