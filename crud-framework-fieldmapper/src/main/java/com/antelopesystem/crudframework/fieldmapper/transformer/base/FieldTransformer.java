package com.antelopesystem.crudframework.fieldmapper.transformer.base;

import java.lang.reflect.Field;

public interface FieldTransformer<FromType, ToType> {

	ToType transform(Field fromField, Field toField, FromType originalValue, Object fromObject, Object toObject);
}
