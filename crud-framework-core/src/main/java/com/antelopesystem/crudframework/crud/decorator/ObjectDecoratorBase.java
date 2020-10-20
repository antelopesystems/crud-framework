package com.antelopesystem.crudframework.crud.decorator;

import java.lang.reflect.ParameterizedType;

public abstract class ObjectDecoratorBase<From, To> implements ObjectDecorator<From, To> {

	@Override
	public final String getType() {
		String tClazz = ((Class<From>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0]).getName();
		String eClazz = ((Class<To>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[1]).getName();

		return tClazz + "_" + eClazz;
	}
}
