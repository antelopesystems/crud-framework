package com.antelopesystem.crudframework.fieldmapper.transformer;

import com.antelopesystem.crudframework.fieldmapper.transformer.base.FieldTransformerBase;

import java.lang.reflect.Field;
import java.util.Date;

public class LongToDateTransformer extends FieldTransformerBase<Long, Date> {

	@Override
	protected Date innerTransform(Field fromField, Field toField, Long originalValue, Object fromObject, Object toObject) {
		return originalValue == null ? null : new Date(originalValue);
	}
}
