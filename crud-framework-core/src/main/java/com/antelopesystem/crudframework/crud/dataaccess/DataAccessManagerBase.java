package com.antelopesystem.crudframework.crud.dataaccess;

import java.lang.reflect.ParameterizedType;

public abstract class DataAccessManagerBase<Accessor, AccessorId, Entity> implements DataAccessManager<Accessor, AccessorId, Entity> {

	@Override
	public String getKey() {
		String accessorClazz = ((Class<Accessor>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[0]).getName();
		String entityClazz = ((Class<Entity>) ((ParameterizedType) getClass()
				.getGenericSuperclass()).getActualTypeArguments()[1]).getName();

		String key = accessorClazz + "_" + entityClazz;
		return key;
	}
}
