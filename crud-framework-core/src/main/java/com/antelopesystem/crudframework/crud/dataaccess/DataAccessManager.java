package com.antelopesystem.crudframework.crud.dataaccess;

import com.antelopesystem.crudframework.utils.component.componentmap.annotation.ComponentMapKey;
import com.antelopesystem.crudframework.modelfilter.DynamicModelFilter;

import java.io.Serializable;

public interface DataAccessManager<Accessor, Entity> {

	@ComponentMapKey
	String getKey();

	default void decorateViewOperation(DynamicModelFilter filter, Serializable accessorId, Class<Accessor> accessorClazz) {
	}

	default void decorateUpdateOperation(DynamicModelFilter filter, Serializable accessorId, Class<Accessor> accessorClazz) {
	}

	default void decorateCreateOperation(Entity entity, Serializable accessorId, Class<Accessor> accessorClazz) {
	}

}
