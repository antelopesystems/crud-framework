package com.antelopesystem.crudframework.fieldmapper.transformer;

import com.antelopesystem.crudframework.fieldmapper.transformer.base.FieldTransformerBase;

import java.lang.reflect.Field;

public class ToStringTransformer extends FieldTransformerBase<Object, String> {

	@Override
	protected String innerTransform(Field fromField, Field toField, Object originalValue, Object fromObject, Object toObject) {
		return originalValue == null ? null : originalValue.toString();
	}
}
