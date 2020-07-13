package com.antelopesystem.crudframework.jpa.transformer;

import com.antelopesystem.crudframework.fieldmapper.transformer.base.FieldTransformerBase;
import com.antelopesystem.crudframework.jpa.model.JpaBaseEntity;

import java.lang.reflect.Field;

public class BaseEntityToIdTransformer extends FieldTransformerBase<JpaBaseEntity, Long> {

	@Override
	protected Long innerTransform(Field fromField, Field toField, JpaBaseEntity originalValue) {
		return originalValue == null ? null : originalValue.getId();
	}
}
