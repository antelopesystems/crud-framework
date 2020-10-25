package com.antelopesystem.crudframework.fieldmapper.transformer;

import com.antelopesystem.crudframework.fieldmapper.transformer.base.FieldTransformerBase;
import com.google.gson.Gson;
import java.lang.reflect.Field;

public class ObjectToJsonTransformer extends FieldTransformerBase<Object, String> {

	private Gson gson = new Gson();

	@Override
	protected String innerTransform(Field fromField, Field toField, Object originalValue, Object fromObject, Object toObject) {
		if(originalValue == null) {
			return null;
		}

		return gson.toJson(originalValue);
	}
}