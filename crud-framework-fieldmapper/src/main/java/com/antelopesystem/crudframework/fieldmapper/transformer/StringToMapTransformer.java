package com.antelopesystem.crudframework.fieldmapper.transformer;

import com.antelopesystem.crudframework.fieldmapper.transformer.base.FieldTransformerBase;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Map;

public class StringToMapTransformer extends FieldTransformerBase<String, Map<String, String>> {

	@Override
	protected Map<String, String> innerTransform(Field fromField, Field toField, String originalValue, Object fromObject, Object toObject) {
		if(originalValue == null) {
			return null;
		}

		Map<String, String> dataMap = new HashMap<>();
		String[] rows = originalValue.split(",");
		for(String row : rows) {
			String[] data = row.split("=");
			dataMap.put(data[0], data[1]);
		}

		return dataMap;
	}
}