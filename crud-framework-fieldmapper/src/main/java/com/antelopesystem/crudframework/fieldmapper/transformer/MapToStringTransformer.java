package com.antelopesystem.crudframework.fieldmapper.transformer;

import com.antelopesystem.crudframework.fieldmapper.transformer.base.FieldTransformerBase;
import java.lang.reflect.Field;
import java.util.Map;

public class MapToStringTransformer extends FieldTransformerBase<Map<String, String>, String> {

	@Override
	protected String innerTransform(Field fromField, Field toField, Map<String, String> originalValue, Object fromObject, Object toObject) {
		if(originalValue == null) {
			return null;
		}

		StringBuilder stringBuilder = new StringBuilder();
		originalValue.entrySet().forEach(x -> stringBuilder.append(x.getKey()).append("=").append(x.getValue()).append(","));

		if(stringBuilder.length() == 0) {
			return "";
		}

		return stringBuilder.deleteCharAt(stringBuilder.length()-1).toString();
	}
}