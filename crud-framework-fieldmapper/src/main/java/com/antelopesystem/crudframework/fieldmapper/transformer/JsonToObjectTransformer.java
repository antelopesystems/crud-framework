package com.antelopesystem.crudframework.fieldmapper.transformer;

import com.antelopesystem.crudframework.fieldmapper.transformer.base.FieldTransformerBase;
import com.google.gson.Gson;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.util.Collection;

public class JsonToObjectTransformer extends FieldTransformerBase<String, Object> {

	private Gson gson = new Gson();

	@Override
	protected Object innerTransform(Field fromField, Field toField, String originalValue, Object fromObject, Object toObject) {
		if(originalValue == null) {
			return null;
		}

		if(Collection.class.isAssignableFrom(toField.getType())) {
			ParameterizedType listType = (ParameterizedType) toField.getGenericType();
			return gson.fromJson(originalValue, listType);
		}

		return gson.fromJson(originalValue, toField.getType());
	}
}