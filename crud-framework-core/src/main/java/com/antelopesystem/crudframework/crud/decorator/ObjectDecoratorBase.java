package com.antelopesystem.crudframework.crud.decorator;

import com.antelopesystem.crudframework.fieldmapper.transformer.base.FieldTransformerBase;
import org.springframework.core.GenericTypeResolver;

import java.lang.reflect.ParameterizedType;

public abstract class ObjectDecoratorBase<From, To> implements ObjectDecorator<From, To> {

	@Override
	public final String getType() {
		Class<?>[] generics = GenericTypeResolver.resolveTypeArguments(getClass(), ObjectDecoratorBase.class);
		String fromClazzName = generics[0].getName();
		String toClazzName = generics[1].getName();

		return fromClazzName + "_" + toClazzName;
	}
}
