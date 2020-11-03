package com.antelopesystem.crudframework.crud.dataaccess;

import com.antelopesystem.crudframework.utils.component.componentmap.annotation.ComponentMapKey;
import com.antelopesystem.crudframework.modelfilter.DynamicModelFilter;

import java.io.Serializable;

public interface DataAccessManager<Accessor, AccessorId, Entity> {

	@ComponentMapKey
	String getKey();

	default void decorateViewOperation(DynamicModelFilter filter, AccessorId accessorId, Class<Accessor> accessorClazz) {
	}

	default void decorateUpdateOperation(DynamicModelFilter filter, AccessorId accessorId, Class<Accessor> accessorClazz) {
	}

	default void decorateCreateOperation(Entity entity, AccessorId accessorId, Class<Accessor> accessorClazz) {
	}

}
