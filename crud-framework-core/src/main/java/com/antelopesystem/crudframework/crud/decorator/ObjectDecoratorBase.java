package com.antelopesystem.crudframework.crud.decorator;

import java.lang.reflect.ParameterizedType;

public abstract class ObjectDecoratorBase<Entity, RO> implements ObjectDecorator<Entity, RO> {

	@Override
	public final String getType() {
		String tClazz = ((Class<Entity>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0]).getName();
		String eClazz = ((Class<RO>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[1]).getName();

		return tClazz + "_" + eClazz;
	}
}
