package com.antelopesystem.crudframework.fieldmapper.transformer;

import com.antelopesystem.crudframework.fieldmapper.transformer.annotation.EnumType;
import com.antelopesystem.crudframework.fieldmapper.transformer.base.FieldTransformerBase;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

public class CommaDelimitedStringToEnumListTransformer extends FieldTransformerBase<String, List> {

	@Override
	protected List innerTransform(Field fromField, Field toField, String originalValue, Object fromObject, Object toObject) {
		if(originalValue == null) {
			return null;
		}

		if(originalValue.isEmpty()) {
			return new ArrayList();
		}

		List<String> enumValues = Arrays.asList(originalValue.split(","));
		Annotation annotation = toField.getAnnotation(EnumType.class);
		if(annotation == null) {
			throw new IllegalStateException("EnumType annotation missing on field - " + toField.toString());
		}
		Class<? extends Enum> clazz = ((EnumType) annotation).value();

		List list = new ArrayList();
		for(String enumValue : enumValues) {
			list.add(Enum.valueOf(clazz, enumValue));
		}
		return list;
	}
}
