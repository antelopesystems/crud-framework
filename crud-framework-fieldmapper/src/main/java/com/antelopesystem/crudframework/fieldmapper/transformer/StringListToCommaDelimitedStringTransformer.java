package com.antelopesystem.crudframework.fieldmapper.transformer;

import com.antelopesystem.crudframework.fieldmapper.transformer.base.FieldTransformerBase;

import java.lang.reflect.Field;
import java.util.List;

public class StringListToCommaDelimitedStringTransformer extends FieldTransformerBase<List, String> {


	@Override
	protected String innerTransform(Field fromField, Field toField, List originalValue) {
		return originalValue == null ? null : String.join(",", originalValue);
	}
}
