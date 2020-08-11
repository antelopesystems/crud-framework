package com.antelopesystem.crudframework.fieldmapper.transformer;


import com.antelopesystem.crudframework.fieldmapper.transformer.base.FieldTransformerBase;

import java.lang.reflect.Field;

public class CurrencyDoubleToLongTransformer extends FieldTransformerBase<Double, Long> {

	@Override
	protected Long innerTransform(Field fromField, Field toField, Double originalValue) {
		return originalValue != null ? Double.valueOf(originalValue * 100.0).longValue() : 0L;
	}
}
