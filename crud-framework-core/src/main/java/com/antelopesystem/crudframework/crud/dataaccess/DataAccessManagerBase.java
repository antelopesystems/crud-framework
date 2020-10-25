package com.antelopesystem.crudframework.crud.dataaccess;

import org.springframework.core.GenericTypeResolver;

public abstract class DataAccessManagerBase<Accessor, AccessorId, Entity> implements DataAccessManager<Accessor, AccessorId, Entity> {

	@Override
	public String getKey() {
		Class[] generics = GenericTypeResolver.resolveTypeArguments(getClass(), DataAccessManagerBase.class);
		String accessorClazz = generics[0].getName();
		String entityClazz = generics[2].getName();

		String key = accessorClazz + "_" + entityClazz;
		return key;
	}
}
