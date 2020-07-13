package com.antelopesystem.crudframework.fieldmapper.transformer;

import com.antelopesystem.crudframework.fieldmapper.transformer.base.FieldTransformerBase;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;

public class CommaDelimitedStringToListTransformer extends FieldTransformerBase<String, List> {

	@Override
	protected List innerTransform(Field fromField, Field toField, String originalValue) {
		return originalValue == null ? null : Arrays.asList(originalValue.split(","));
	}
}
