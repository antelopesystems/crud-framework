package com.antelopesystem.crudframework.fieldmapper.transformer;

import com.antelopesystem.crudframework.fieldmapper.transformer.base.FieldTransformerBase;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class JsonStringToMapTransformer extends FieldTransformerBase<String, Map<String, Object>> {

	private Gson gson = new Gson();

	@Override
	protected Map<String, Object> innerTransform(Field fromField, Field toField, String originalValue, Object fromObject, Object toObject) {
		if(originalValue == null) {
			return null;
		}

		if(originalValue.trim().isEmpty()) {
			return new HashMap<>();
		}

		return gson.fromJson(originalValue, new TypeToken<Map<String, Object>>(){}.getType());
	}
}