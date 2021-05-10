package com.antelopesystem.crudframework.fieldmapper.transformer;

import com.antelopesystem.crudframework.fieldmapper.transformer.base.FieldTransformerBase;

import java.lang.reflect.Field;
import java.util.Date;

public class DateToLongTransformer extends FieldTransformerBase<Date, Long> {
	@Override
	protected Long innerTransform(Field fromField, Field toField, Date originalValue, Object fromObject, Object toObject) {
		return originalValue == null ? null : originalValue.getTime();
	}
}
