package com.antelopesystem.crudframework.fieldmapper.transformer;

import com.antelopesystem.crudframework.fieldmapper.transformer.base.FieldTransformerBase;

import java.lang.reflect.Field;
import java.util.List;
import java.util.stream.Collectors;

public class EnumListToCommaDelimitedString extends FieldTransformerBase<List, String> {

	@Override
	protected String innerTransform(Field fromField, Field toField, List originalValue) {
		if(originalValue == null) {
			return null;
		}

		if(originalValue.isEmpty()) {
			return "";
		}

		List<Enum> enumList = originalValue;
		return enumList.stream().map(f -> f.name()).collect(Collectors.joining(","));
	}
}
