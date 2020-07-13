package com.antelopesystem.crudframework.fieldmapper.transformer;

import com.antelopesystem.crudframework.exception.tree.core.ErrorCode;
import com.antelopesystem.crudframework.fieldmapper.transformer.annotation.EnumType;
import com.antelopesystem.crudframework.fieldmapper.transformer.base.FieldTransformerBase;
import com.antelopesystem.crudframework.fieldmapper.transformer.exception.TransformationException;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommaDelimitedStringToEnumListTransformer extends FieldTransformerBase<String, List> {

	@Override
	protected List innerTransform(Field fromField, Field toField, String originalValue) {
		if(originalValue == null) {
			return null;
		}

		if(originalValue.isEmpty()) {
			return new ArrayList();
		}

		List<String> enumValues = Arrays.asList(originalValue.split(","));
		Annotation annotation = toField.getAnnotation(EnumType.class);
		if(annotation == null) {
			throw new TransformationException().withDisplayMessage("EnumType annotation missing on field - " + toField.toString()).withErrorCode(ErrorCode.FieldTypeMismatch);
		}
		Class<? extends Enum> clazz = ((EnumType) annotation).value();

		List list = new ArrayList();
		for(String enumValue : enumValues) {
			list.add(Enum.valueOf(clazz, enumValue));
		}
		return list;
	}
}
