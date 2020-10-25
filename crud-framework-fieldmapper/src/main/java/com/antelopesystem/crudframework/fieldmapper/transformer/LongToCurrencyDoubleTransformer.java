package com.antelopesystem.crudframework.fieldmapper.transformer;


import com.antelopesystem.crudframework.fieldmapper.transformer.base.FieldTransformerBase;

import java.lang.reflect.Field;

public class LongToCurrencyDoubleTransformer extends FieldTransformerBase<Long, Double> {

	@Override
	protected Double innerTransform(Field fromField, Field toField, Long originalValue, Object fromObject, Object toObject) {
		return originalValue != null ? originalValue / 100.0 : 0.0;
	}
}
