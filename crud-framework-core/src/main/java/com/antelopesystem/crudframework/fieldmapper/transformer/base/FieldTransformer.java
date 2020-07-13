package com.antelopesystem.crudframework.fieldmapper.transformer.base;

import java.lang.reflect.Field;

public interface FieldTransformer<T, E> {

	E transform(Field fromField, Field toField, T originalValue);
}
