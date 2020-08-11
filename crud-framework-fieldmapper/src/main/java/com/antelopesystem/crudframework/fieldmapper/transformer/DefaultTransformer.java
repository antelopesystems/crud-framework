package com.antelopesystem.crudframework.fieldmapper.transformer;

import com.antelopesystem.crudframework.fieldmapper.transformer.base.FieldTransformer;

import java.lang.reflect.Field;

public class DefaultTransformer implements FieldTransformer<Object, Object> {

	@Override
	public Object transform(Field fromField, Field toField, Object originalValue) {
		return originalValue;
	}
}
