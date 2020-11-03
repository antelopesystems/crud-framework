package com.antelopesystem.crudframework.jpa.transformer;

import com.antelopesystem.crudframework.fieldmapper.transformer.base.FieldTransformerBase;
import com.antelopesystem.crudframework.jpa.model.BaseJpaEntity;

import java.lang.reflect.Field;

public class BaseEntityToIdTransformer extends FieldTransformerBase<BaseJpaEntity, Long> {

	@Override
	protected Long innerTransform(Field fromField, Field toField, BaseJpaEntity originalValue, Object fromObject, Object toObject) {
		return originalValue == null ? null : originalValue.getId();
	}
}
